OpenLayers.Control.RZoomControl = OpenLayers.Class(OpenLayers.Control, {
    handler:null,

    /*
    * false - zoom in
    * true - zoom out
    */
    out: false,

    initialize:function() {
        OpenLayers.Control.prototype.initialize.apply(this, arguments);
    },

    draw: function() {
        this.handler = new OpenLayers.Handler.RBox(this, {keyMask: this.keyMask});
        this.permanentDragPan = new OpenLayers.Control.RDragPan({map: this.map});
        this.permanentDragPan.draw();
        this.permanentDragPan.setMouseClickMode(false);
        this.activate();
    },

    activate : function() {
        OpenLayers.Control.prototype.activate.apply(this, arguments);
        this.permanentDragPan.activate();
    },

    deactivate : function() {
        this.permanentDragPan.deactivate();
        OpenLayers.Control.prototype.deactivate.apply(this, arguments);
    },

    CLASS_NAME: "OpenLayers.Control.RZoomControl"
})