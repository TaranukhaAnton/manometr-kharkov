/**
 * @requires OpenLayers/Control.js
 */

/**
 * Created by Golushkov Oleg
 *
 * Class: OpenLayers.Control.CenterAt
 *
 * Inherits from:
 *  - <OpenLayers.Control>
 */
OpenLayers.Control.CenterAt = OpenLayers.Class(OpenLayers.Control, {

    /**
     * Constructor: OpenLayers.Control.CenterAt
     * Create a new CenterAt control
     *
     */
    initialize: function() {
        this.moved = false;
        OpenLayers.Control.prototype.initialize.apply(this, arguments);
    },

    /**
     * Mouse down click handler
     * @param evt
     */
    clickHandler : function(evt) {
        if (OpenLayers.Event.isRightClick(evt) && !this.getMoved()) {
            var point = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point());
            var lonlat = this.map.getLonLatFromPixel(evt.xy);
            this.map.setCenter(lonlat, this.map.getZoom(), null, false);
            return false;
        }
        if ((evt.srcElement != undefined && evt.srcElement.href!=undefined && evt.srcElement.href.indexOf("zoom-minus-mini.png") != -1)
                || (evt.target != undefined && evt.target.src.indexOf("zoom-minus-mini.png") != -1)){
            this.map.zoomTo(this.map.getZoom()-1);
        }
        this.moved = false;
        this.rightClicked = false;
    },

    getMoved : function() {
        return this.moved;
    },

    /**
     * Sets if mouse was moved with clicked right button, then do nothing.
     * @param evt
     */
    moveHandler : function(evt) {
        if (this.map.div.style.cursor == "move"){
            this.moved = true;
        }
    },


    /**
     * APIMethod: activate
     * Activate the control. Registers map click listener
     *
     * Returns:
     * {Boolean} Control successfully activated.
     */
    activate: function() {
        var activated = false;
        if (this.map) {
            if (OpenLayers.Control.prototype.activate.apply(this)) {
                activated = true;
                this.map.events.register("mouseup", this, this.clickHandler);
                this.map.events.register("mousemove", this, this.moveHandler);
            }
        }
        return activated;
    },

    /**
     * APIMethod: deactivate
     * Deactivate the control.  This unregisters map click listener.
     *
     * Returns:
     * {Boolean} Control successfully deactivated.
     */
    deactivate: function() {
        var deactivated = false;
        if (this.map) {
            if (OpenLayers.Control.prototype.deactivate.apply(this)) {
                this.map.events.unregister("mouseup", this, this.clickHandler);
                this.map.events.unregister("mousemove", this, this.clickHandler);
                deactivated = true;
            }
        }
        return deactivated;
    },

    /**
     * Method: draw
     */
    draw: function() {
        this.map.div.oncontextmenu = function () {
            return false;
        };
        this.activate();
    },


    CLASS_NAME: "OpenLayers.Control.CenterAt"
});