OpenLayers.Control.RNavigation = OpenLayers.Class(OpenLayers.Control.Navigation, {
    initialize: function() {
        OpenLayers.Control.Navigation.prototype.initialize.apply(this, arguments);
    },

    /**
     * Method: draw
     */
    draw: function() {
        var clickCallbacks = {
            'dblclick': this.defaultDblClick,
            'dblrightclick': this.defaultDblRightClick
        };
        var clickOptions = {
            'double': true,
            'stopDouble': true
        };
        this.handlers.click = new OpenLayers.Handler.Click(
            this, clickCallbacks, clickOptions
        );
        this.dragPan = new OpenLayers.Control.RDragPan(
            OpenLayers.Util.extend({map: this.map}, this.dragPanOptions)
        );
        this.zoomBox = new OpenLayers.Control.ZoomBox(
                    {map: this.map, keyMask: OpenLayers.Handler.MOD_SHIFT});
        this.dragPan.draw();
        this.dragPan.setMouseClickMode(false);
        this.zoomBox.draw();
        this.handlers.wheel = new OpenLayers.Handler.MouseWheel(
                                    this, {"up"  : this.wheelUp,
                                           "down": this.wheelDown} );
        this.activate();
    },

    CLASS_NAME: "OpenLayers.Control.RNavigation"
})