var PAGE_SIZE = 10;
var TIMEOUT = 60000 * 3;
function dealErrResp(resp, opt) {
	Ext.MessageBox.show({
				title : '请求错误',
				msg : resp.statusText,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
}

Ext.EventManager.on(Ext.isIE ? document : window, 'keydown', function(e, t) {
    if (e.getKey() == e.BACKSPACE &&(t.disabled || t.readOnly)) {
        e.stopEvent();
    }
});

function beforeQueryCombo(qe) {
	var combo = qe.combo;
    var q = qe.query;
    var forceAll = qe.forceAll;
    if (forceAll === true || (q.length >= this.minChars)) {
        if (this.lastQuery !== q) {
            this.lastQuery = q;
            if (this.mode == 'local') {
                this.selectedIndex = -1;
                if (forceAll) {
                    this.store.clearFilter();
                } else {
                    // 检索的正则
                    var regExp = new RegExp(".*" + q + ".*", "i");
                    // 执行检索
                    this.store.filterBy(function(record, id) {
                        // 得到每个record的项目名称值
                        var text = record.get(combo.displayField);
                        return regExp.test(text);
                    });
                }
                this.onLoad();
            } else {
                this.store.baseParams[this.queryParam] = q;
                this.store.load({
                    params: this.getParams(q)
                });
                this.expand();
            }
        } else {
            this.selectedIndex = -1;
            this.onLoad();
        }
    }
    return false;
}


/** 设置combobox宽度 */
Ext.override(Ext.form.ComboBox, {
    initComponent: Ext.form.ComboBox.prototype.initComponent.createInterceptor(function () {
    	if(this.fieldLabel){
	        if(this.fieldLabel.indexOf('品名')>=0){
	        	this.listWidth = 300;
	        }
    	}
    })
});
/** 设置分页页数 */
var maxPage = 50;