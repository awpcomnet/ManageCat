/**
 * 描述: 
 * 作者: 
 */
Ext.define("MIS.store.mailuser.MailUserStore", {
    extend: "Ext.data.Store",

    model: "MIS.model.mailuser.MailUser",

    proxy: {
        type: "ajax",
        url: "/mailuser/query",
        reader: {
            type: "json",
            root: "results",
			totalProperty: "meta.totalRecord"
        },
        extraParams: {
            type: "0"
        }
    }
});
