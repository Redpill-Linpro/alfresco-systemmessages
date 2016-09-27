<@markup id="js">
<#-- JavaScript Dependencies -->
    <#include "/org/alfresco/components/form/form.js.ftl"/>
    <@script src="${url.context}/res/redpill/components/system-messages/system-messages-admin.js" group="console"/>
    <@script src="${url.context}/res/modules/simple-dialog.js" group="console"/>
    <@script src="${url.context}/res/components/console/consoletool.js" group="console"/>
</@>

<@markup id="widgets">
<@createWidgets group="console"/>
</@>

<@markup id="html">
<@uniqueIdDiv>
<#assign el=args.htmlid?html>
    <div class="title">System Messages</div>

    <div class="dashlet messages-list">
        <div class="title">System Messages</div>
        <div id="${el}-messages-list-info" class="tags-list-info"></div>
        <div id="${el}-messages-list-bar-bottom" class="toolbar theme-bg-color-3 hidden">
            <div id="${el}-paginator" class="paginator hidden">&nbsp;</div>
        </div>
        <div id="${el}-messages" class="body scrollableList" style="height: 100%; overflow: hidden"></div>
    </div>
    <input type="button" id="${el}-new-message" value="${msg('new.message')}">
</@>
</@>
