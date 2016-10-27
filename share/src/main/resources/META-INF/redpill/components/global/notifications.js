/**
 * System notification
 *
 */
(function(){ //function scope

var Dom      = YAHOO.util.Dom;
var Event    = YAHOO.util.Event;
var Cookie   = YAHOO.util.Cookie;
var Bubbling = YAHOO.Bubbling;

/**
 * System messages
 * 
 */
var SystemNotifications = function() {
    //Do an ajax request to check for messagexs
    Alfresco.util.Ajax.jsonGet({
        url: '/share/proxy/alfresco/api/redpill/notifications?active=true',
        successCallback: { 
            fn:  (function (res) {
                    if (res.json && res.json.notifications) {
                        //If the server is really fast we get a response before the DOM is ready, at least its possible theoretically
                        Event.onDOMReady((function(){
                            this._callback(res.json.notifications);
                        }).bind(this));
                    }
                }).bind(this)
           }
    });
    
};

SystemNotifications.prototype._callback = function(data) {
    for (var i=0; i<data.length; i++) {
        var msg = data[i];
        //is it read?
        if (!this._readCookie(msg.id)) {
            if (msg.type !== 'High') {
                msg.close = (function(n){ this.setRemovalCookie(n.id,msg); }).bind(this);
            }
            Bubbling.fire('notifications.notify',msg);
        }
    }
};


SystemNotifications.prototype._readCookie = function(id) {
    var read = Cookie.get('deletednotifications' + id);
    return read;
};


SystemNotifications.prototype.setRemovalCookie = function(id,msg) {
    var read = this._readCookie(id);
    var expiryDate = new Date();
    // Expires in one day or at the same time as the message is no longer active.
    expiryDate.setTime( expiryDate.getTime() + 86400000 );
    if(msg.endTime)
    {
       expiryDate = new Date(msg.endTime);
    }
    Cookie.set("deletednotifications" + id, "read", {
        expires:  expiryDate
    });
};


var template = '<div id="{id}" class="notification {type}">{close}<span class="title">{title}</span>{text}</div>';
var notifications_div;
Bubbling.on("notifications.notify",function(layer,payload){
    
    //check if we have a div to put them in, otherwise create one
    if (!notifications_div) {
        notifications_div = document.createElement('div');
        notifications_div.id = "notifications";
        Dom.insertBefore(notifications_div,'bd');
    }
    var n = payload[1]; //this is just how Bubbling is set up
    
    //sanity, we don't wan't the message 'undefined' or 'null' 
    n = {
            id: n.id?n.id:"",
            title: n.title?n.title:'',
            text:  n.text?n.text:'',
            type:  n.type?n.type:'normal', 
            close: n.close
    };
    
    
    //create div and fill it from the template    
    var tmp = document.createElement('div');
    tmp.innerHTML = template.replace('{id}',n.id)
                            .replace('{type}',n.type.toLowerCase())
                            .replace('{title}',n.title)
                            .replace('{text}',n.text)
                            .replace('{close}',n.close?'<a href="#" class="close"></a>':'');
                            
    //add to notifications
    n.node = Dom.getFirstChild(tmp);
    notifications_div.appendChild(n.node);

    if (n.close) {
        //hook up close event
        Dom.getElementsByClassName('close','a',n.node,function(a){
            Event.on(a,'click',function(e){
                Event.stopPropagation(e);
                
                //Dom.addClass(n.node,'hidden');
                Alfresco.util.Anim.fadeOut(n.node);
                //do callback
                if (YAHOO.lang.isFunction(n.close)) {
                    n.close(n);
                }
            });
        });
    }
});

/**
 * Start it all off
 * 
 */

var currentUser = Alfresco.constants.USERNAME.toLowerCase();
if (currentUser !== "guest"){
	var system  = new SystemNotifications();
}


})();
