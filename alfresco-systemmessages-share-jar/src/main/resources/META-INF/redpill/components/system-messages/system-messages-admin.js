var Redpill = Redpill || {};
Alfresco.logger.debug("system-messages-admin.js");

Redpill.SystemMessages = function (htmlId) {
    Redpill.SystemMessages.superclass.constructor.call(this,
        "Redpill.SystemMessages",
        htmlId,
        ["button", "container", "datasource", "datatable", "paginator", "history", "animation"]
    );

    return this;
};

YAHOO.extend(Redpill.SystemMessages, Alfresco.component.Base, {

  notificationsUrl: Alfresco.constants.PROXY_URI + "api/redpill/notifications",

    onReady: function () {
        Alfresco.logger.debug("onReady", arguments);
        Alfresco.util.Ajax.jsonGet({
            url: Alfresco.constants.PROXY_URI + "api/redpill/notifications/datalist",
            responseContentType: Alfresco.util.Ajax.JSON,
            successCallback: {
                fn: function (response) {
                    console.log(response);
                    this.dataList = response.json.nodeRef;
                },
                scope: this
            },
            failureMessage: this.msg("operation.failed")
        });
        this.setupDataTable();
        this.setupNewMessageButton();
        Alfresco.logger.debug("END onReady");
    },
    setupNewMessageButton: function () {
        this.widgets.newMessageButton =
            new YAHOO.widget.Button(this.id + "-new-message");
        this.widgets.newMessageButton.on(
            "click",
            function () {
                this.onNewRow();
            },
            null,
            this
        );
    },
    actionFormatter: function (elCell, oRecord, oColumn, oData) {
        Alfresco.logger.debug("actionFormatter", arguments);
        var nodeRef = oRecord.getData().nodeRef;
        elCell.innerHTML = "<div class='action'>" +
            "<a class='edit-message'>" + this.msg("button.edit") + "</a>" +
            " | <a class='delete-message'>" + this.msg("button.delete") + "</a>" +
            "</div>";
        Alfresco.logger.debug("END actionFormatter");
    },
    timeFormatter: function (elCell, oRecord, oColumn, oData) {
        var timeISODateTime = oData;
        if (timeISODateTime !== null)
        {
           var time = Alfresco.util.fromISO8601(timeISODateTime);
           elCell.innerHTML = Alfresco.util.formatDate(time);
        }
        else
        {
           elCell.innerHTML = this.msg("label.none");
        }
    },
    
    setupDataTable: function () {
        Alfresco.logger.debug("setupDataTable", arguments);
        var columnDefinitions = [
            {
                key: "title",
                label: this.msg("title.title"),
                sortable: false,
            },
            {
                key: "text",
                label: this.msg("title.text"),
                sortable: false,
            },
            {
                key: "startTime",
                label: this.msg("title.startTime"),
                sortable: true,
                formatter: this.timeFormatter.bind(this)
            },
           {
                key: "endTime",
                label: this.msg("title.endTime"),
                sortable: true,
                formatter: this.timeFormatter.bind(this)
            },
            {
                key: "type",
                label: this.msg("title.type"),
                sortable: false,
            },
            {
                key: "actions",
                label: this.msg("title.actions"),
                sortable: false,
                formatter: this.actionFormatter.bind(this)
            }
        ];

        // DataSource definition

        var dataSource = this.widgets.dataSource =
            new YAHOO.util.DataSource(this.notificationsUrl, {
                responseType: YAHOO.util.DataSource.TYPE_JSON,
                connXhrMode: "queueRequests",
                responseSchema: {
                    resultsList: "notifications",
                    fields: ["id","nodeRef", "title", "text", "startTime","endTime", "type"]

                }
            });
        // DataTable definition
        var dataTable = this.widgets.dataTable =
            new YAHOO.widget.DataTable(
                    this.id + "-messages",
                columnDefinitions,
                dataSource,
                {
                    initialLoad: {},
                    MSG_LOADING: this.msg("loading.messages"),
                    MSG_EMPTY: this.msg("no.messages.found")
                }
            );

        // Enables row highlighting
        dataTable.subscribe("rowMouseoverEvent", dataTable.onEventHighlightRow);
        dataTable.subscribe("rowMouseoutEvent", dataTable.onEventUnhighlightRow);

        // Attach event to update links
        YAHOO.util.Event.delegate(
            this.id,
            "click",
            this.onEditRow.bind(this),
            "a.edit-message"
        );

        // Attach event to delete links
        YAHOO.util.Event.delegate(
            this.id,
            "click",
            this.onDeleteRow.bind(this),
            "a.delete-message"
        );
    },

    onDeleteRow:function(event,element)
    {
       var tr = element.parentNode.parentNode.parentNode;
       var record = this.widgets.dataTable.getRecord(tr);
       var parsedNodeRef = Alfresco.util.NodeRef(record.getData().nodeRef);
       var url = YAHOO.lang.substitute(
               Alfresco.constants.PROXY_URI +
               "api/redpill/delete/{storeType}/{storeId}/{id}",
           parsedNodeRef
       );
       if(confirm(this.msg("message.delete.confirm")))
       {
          Alfresco.util.Ajax.jsonDelete({
               url: url,
               responseContentType: Alfresco.util.Ajax.JSON,
               successCallback: {
                   fn: function (response) {
                     Alfresco.util.PopupManager.displayMessage(
                     {
                        text: this.msg("message.delete.success",record.getData().title)
                     });
                     this.widgets.dataTable.deleteRow(record);
                   },
                   scope: this
               },
               failureMessage: this.msg("operation.failed")
           });
       }
    },

      onNewRow: function DataListToolbar_onNewRow(e, p_obj)
      {
         var destination = this.dataList;
         var itemType = "rlsm:systemMessage";
         // Intercept before dialog show
         var doBeforeDialogShow = function DataListToolbar_onNewRow_doBeforeDialogShow(p_form, p_dialog)
         {
            Alfresco.util.populateHTML(
               [ p_dialog.id + "-dialogTitle", this.msg("label.new-row.title") ],
               [ p_dialog.id + "-dialogHeader", this.msg("label.new-row.header") ]
            );
         };

         var templateUrl = YAHOO.lang.substitute(Alfresco.constants.URL_SERVICECONTEXT + "components/form?itemKind={itemKind}&itemId={itemId}&destination={destination}&mode={mode}&submitType={submitType}&showCancelButton=true",
         {
            itemKind: "type",
            itemId: itemType,
            destination: destination,
            mode: "create",
            submitType: "json"
         });

         // Using Forms Service, so always create new instance
         var createRow = new Alfresco.module.SimpleDialog(this.id + "-createRow");

         createRow.setOptions(
         {
            templateUrl: templateUrl,
            actionUrl: null,
            destroyOnHide: true,
            doBeforeDialogShow:
            {
               fn: doBeforeDialogShow,
               scope: this
            },
            onSuccess:
            {
               fn: function DataListToolbar_onNewRow_success(response)
               {
                  var data = response.config.dataObj;
                  var nodeRef = Alfresco.util.NodeRef(response.json.persistedObject);
                  var item =
                  {
                     id:nodeRef.id,
                     nodeRef:nodeRef.toString(),
                     title:data.prop_rlsm_systemMessageTitle,
                     text:data.prop_rlsm_systemMessageDescription,
                     type:data.prop_rlsm_systemMessagePriority,
                     startTime:data.prop_rlsm_systemMessageStartTime,
                     endTime:data.prop_rlsm_systemMessageEndTime
                  };
                  Alfresco.util.PopupManager.displayMessage(
                  {
                     text: this.msg("message.new-row.success")
                  });
                  this.widgets.dataTable.addRow(item);
               },
               scope: this
            },
            onFailure:
            {
               fn: function DataListToolbar_onNewRow_failure(response)
               {
                  Alfresco.util.PopupManager.displayMessage(
                  {
                     text: this.msg("message.new-row.failure")
                  });
               },
               scope: this
            }
         }).show();
      },
      onEditRow:function(event,element)
      {
           var tr = element.parentNode.parentNode.parentNode;
           var record = this.widgets.dataTable.getRecord(tr)
           var data = record.getData();
           var destination = this.dataList;
           var itemType = "rlsm:systemMessage";
           // Intercept before dialog show
           var doBeforeDialogShow = function(p_form, p_dialog)
           {
              Alfresco.util.populateHTML(
                 [ p_dialog.id + "-dialogTitle", this.msg("label.new-row.title") ],
                 [ p_dialog.id + "-dialogHeader", this.msg("label.new-row.header") ]
              );
           };

           var templateUrl = YAHOO.lang.substitute(Alfresco.constants.URL_SERVICECONTEXT + "components/form?submitType=json&itemKind={itemKind}&itemId={itemId}&mode={mode}&showCancelButton=true",
           {
              itemKind: "node",
              itemId: data.nodeRef,
              mode: "edit",
           });

           var editRow = new Alfresco.module.SimpleDialog(this.id + "-editRow");
           editRow.setOptions(
           {
              templateUrl: templateUrl,
              actionUrl: null,
              destroyOnHide: true,
              doBeforeDialogShow:
              {
                 fn: doBeforeDialogShow,
                 scope: this
              },
              onSuccess:
              {
                 fn: function(response)
                 {
                 var dataObj = response.config.dataObj;
                 data.text = dataObj.prop_rlsm_systemMessageDescription;
                 data.title = dataObj.prop_rlsm_systemMessageTitle;
                 data.type = dataObj.prop_rlsm_systemMessagePriority;
                 data.startTime = dataObj.prop_rlsm_systemMessageStartTime;
                 data.endTime = dataObj.prop_rlsm_systemMessageEndTime;
                    Alfresco.util.PopupManager.displayMessage(
                    {
                       text: this.msg("message.edit.success")
                    });
                this.widgets.dataTable.updateRow(record, data);
                 },
                 scope: this
              },
              onFailure:
              {
                 fn: function (response)
                 {
                    Alfresco.util.PopupManager.displayMessage(
                    {
                       text: this.msg("message.edit.failure")
                    });
                 },
                 scope: this
              }
           }).show();
      }
});
