/**
 * System notification
 *
 */
(function(){ //function scope

var Dom      = YAHOO.util.Dom;
var Event    = YAHOO.util.Event;
var Cookie   = YAHOO.util.Cookie;
var Bubbling = YAHOO.Bubbling;


//var template = '<div id="se.vgregion.alfresco:alfresco-vgr-share:amp:3.9.0-SNAPSHOT" class="notifcation" style="margin-top: 10px; padding: 15px; margin-left: 10px; margin-right: 10px;"> <a class="theme-color-2 invite-yes" href="#" style="padding-left: 20px; font-weight: bold;">Ja tack! </a><a href="#" class="theme-color-2 invite-no" style="padding-left: 10px;">Nej inte nu. </a></div>';


        

/**
 * System messages
 * 
 */
var SystemNotifications = function() {
    this.read = {};
    
    //check cookie for already read notifications
    var ids = this._readCookie();
    for (var i=0; i<ids.length; i++) {
        this.read[ids[i]] = true;
    }
    
    var me = this;
    //Do an ajax request to check for messagexs
    Alfresco.util.Ajax.jsonGet({
        url: '/share/proxy/alfresco/redpill/notifications',
        successCallback: { 
            fn:  function (res) {
                    if (res.json && res.json.notifications) {
                        //If the server is really fast we get a response before the DOM is ready, at least its possible theoretically
                        Event.onDOMReady(function(){
                            me._callback(res.json.notifications);
                        });
                    }
                }
           }
    });
    
};

SystemNotifications.prototype._callback = function(data) {
    var blocked = [];
    for (var i=0; i<data.length; i++) {
        var msg = data[i];
        
        //is it read?
        if (!this.read[msg.id]) {
            var me    = this;
            if (msg.type !== 'High') {
                msg.close = function(n){ me.setRemovalCookie(n.id); };
            }
            Bubbling.fire('notifications.notify',msg);
        } else {
            blocked.push(msg.id);
        }
        
    }
    
    //do a clean of the cookie, since we theoretically could just add and add and add
    //we just set it again, but only with blocked messages, i.e. messages we blocked
    //but should be viewed!
    if (blocked.length > 0) { 
        var now = new Date();
        Cookie.set("deletednotifications", blocked.join(','), {
            expires: new Date(now.getFullYear(),(now.getMonth()+1) % 12,now.getDay()) //a month from now is permanent enough
        });
    } else {
        Cookie.remove("deletednotifications");
    }
};


SystemNotifications.prototype._readCookie = function() {
    var read = Cookie.get('deletednotifications');
    read     = read?read.split(','):[];        
    return read;
};


SystemNotifications.prototype.setRemovalCookie = function(id) {
    var read = this._readCookie();   
    read.unshift(id);
    var now = new Date();
    Cookie.set("deletednotifications", read.join(','), {
        expires: new Date(now.getFullYear(),(now.getMonth()+1) % 12,now.getDay()) //a month from now is permanent enough
    });
};


//Notifications via Bubbling events
//API
/** 
  "notifications.notify", 
 {
   id: String,   //(optional), will be used as the id of the surrounding div
   title: "System maintenance", 
   text:   "The system is going down for maintenance at 14.00 to 15.00",
   type:  "warning",   //(optional) defaults to info and is used as a class on the surrounding div of the notification
   close: Boolean|Function, //(optional) if set to true a close button will be added, if a function the button will be added and function used as callback
 }
 
 Usage:
    YAHOO.Bubbling.fire('notifications.notify',{title:"System is going down!", text: "System is going down for maintenance",type:"warning"});
 
**/


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

//helper functions
//gets username from url, null if we're not on a site or we're not on the dashboard
var username = function() {
    var matches = window.location.pathname.match(/.*\/page\/user\/(.*)\/dashboard/);
     if ( matches !== null && matches.length > 1) {
        return matches[1];
     }
     return null;
};

//first check if we're on the dashboard. Don't do anything otherwise
var user  = username();
if (user) {
    
    //fire of ajax requests to fetch notifications
    //we have two sources - user defined in data lists or invites
    var system  = new SystemNotifications();
}


})();
