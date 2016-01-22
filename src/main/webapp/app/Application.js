Ext.define('MIS.Application', {
    name: 'MIS',

    extend: 'Ext.app.Application',
    
    requires: [
               "Ext.data.Request",
               "Ext.window.MessageBox",
               
    ],

    views: [
            
    ],

    controllers: [
                  "MIS.controller.mainviewport.MainNavigation",
                  "MIS.controller.catalog.CatalogController"
    ],

    stores: [
        // TODO: add stores here
    ]
});
