/* Copyright (c) 2006-2008 MetaCarta, Inc., published under the Clear BSD
 * license.  See http://svn.openlayers.org/trunk/openlayers/license.txt for the
 * full text of the license. */

/**
 * @requires OpenLayers/Handler.js
 * @requires OpenLayers/Handler/Drag.js
 */

/**
 * Class: OpenLayers.Handler.Box
 * Handler for dragging a rectangle across the map.  Box is displayed
 * on mouse down, moves on mouse move, and is finished on mouse up.
 *
 * Inherits from:
 *  - <OpenLayers.Handler>
 */
OpenLayers.Handler.RBox = OpenLayers.Class(OpenLayers.Handler, {

    /**
     * Property: dragHandler
     * {<OpenLayers.Handler.Drag>}
     */
    dragHandler: null,

    /**
     * APIProperty: boxDivClassName
     * {String} The CSS class to use for drawing the box. Default is
     *     olHandlerBoxZoomBox
     */
    boxDivClassName: 'olHandlerBoxZoomBox',

    /**
     * Constructor: OpenLayers.Handler.Box
     *
     * Parameters:
     * control - {<OpenLayers.Control>}
     * callbacks - {Object} An object containing a single function to be
     *                          called when the drag operation is finished.
     *                          The callback should expect to recieve a single
     *                          argument, the point geometry.
     * options - {Object}
     */
    initialize: function(control, callbacks, options) {
        OpenLayers.Handler.prototype.initialize.apply(this, [control, callbacks, options]);
        this.callbacks["done"] = this.doZoomBox;

        var callbacks = {
            "down": this.startBox,
            "move": this.moveBox,
            "up":   this.endBox,
            "cancel":   this.cancelBox,
            "out":  this.cancelBox
        };
        this.dragHandler = new OpenLayers.Handler.RDrag(
                this, callbacks, {keyMask: this.keyMask});
    },

    /**
     * Method: setMap
     */
    setMap: function (map) {
        OpenLayers.Handler.prototype.setMap.apply(this, arguments);
        if (this.dragHandler) {
            this.dragHandler.setMap(map);
        }
    },

    /**
     * Method: startBox
     *
     * Parameters:
     * evt - {Event}
     */
    startBox: function (xy) {
        this.zoomBox = OpenLayers.Util.createDiv('zoomBox',
                this.dragHandler.start);
        this.zoomBox.className = this.boxDivClassName;
        this.zoomBox.style.zIndex = this.map.Z_INDEX_BASE["Popup"] - 1;
        this.map.viewPortDiv.appendChild(this.zoomBox);

        // TBD: use CSS classes instead
        this.map.div.style.cursor = "crosshair";
    },

    /**
     * Method: moveBox
     */
    moveBox: function (xy) {
        var deltaX = Math.abs(this.dragHandler.start.x - xy.x);
        var deltaY = Math.abs(this.dragHandler.start.y - xy.y);
        this.zoomBox.style.width = Math.max(1, deltaX) + "px";
        this.zoomBox.style.height = Math.max(1, deltaY) + "px";
        if (xy.x < this.dragHandler.start.x) {
            this.zoomBox.style.left = xy.x + "px";
        }
        if (xy.y < this.dragHandler.start.y) {
            this.zoomBox.style.top = xy.y + "px";
        }
    },

    /**
     * Method: zoomBox
     *
     * Parameters:
     * position - {<OpenLayers.Bounds>} or {<OpenLayers.Pixel>}
     */
    doZoomBox: function (position) {
        if (position instanceof OpenLayers.Bounds) {
            if (!this.out) {
                var minXY = this.map.getLonLatFromPixel(
                        new OpenLayers.Pixel(position.left, position.bottom));
                var maxXY = this.map.getLonLatFromPixel(
                        new OpenLayers.Pixel(position.right, position.top));
                var bounds = new OpenLayers.Bounds(minXY.lon, minXY.lat,
                        maxXY.lon, maxXY.lat);
            } else {
                var pixWidth = Math.abs(position.right - position.left);
                var pixHeight = Math.abs(position.top - position.bottom);
                var zoomFactor = Math.min((this.map.size.h / pixHeight),
                        (this.map.size.w / pixWidth));
                var extent = this.map.getExtent();
                var center = this.map.getLonLatFromPixel(
                        position.getCenterPixel());
                var xmin = center.lon - (extent.getWidth() / 2) * zoomFactor;
                var xmax = center.lon + (extent.getWidth() / 2) * zoomFactor;
                var ymin = center.lat - (extent.getHeight() / 2) * zoomFactor;
                var ymax = center.lat + (extent.getHeight() / 2) * zoomFactor;
                var bounds = new OpenLayers.Bounds(xmin, ymin, xmax, ymax);
            }
            this.map.zoomToExtent(bounds);
        } else { // it's a pixel
            if (!this.out) {
                this.map.setCenter(this.map.getLonLatFromPixel(position),
                        this.map.getZoom() + 1);
            } else {
                this.map.setCenter(this.map.getLonLatFromPixel(position),
                        this.map.getZoom() - 1);
            }
        }
    },

    /**
     * Method: endBox
     */
    endBox: function(end) {
        var result;
        if (Math.abs(this.dragHandler.start.x - end.x) > 5 ||
            Math.abs(this.dragHandler.start.y - end.y) > 5) {
            var start = this.dragHandler.start;
            var top = Math.min(start.y, end.y);
            var bottom = Math.max(start.y, end.y);
            var left = Math.min(start.x, end.x);
            var right = Math.max(start.x, end.x);
            result = new OpenLayers.Bounds(left, bottom, right, top);
        } else {
            result = this.dragHandler.start.clone();
            // i.e. OL.Pixel
        }
        this.removeBox();

        // TBD: use CSS classes instead
        this.map.div.style.cursor = "";
        this.callback("done", [result]);
    },

    cancelBox: function() {
        this.removeBox();
    },


    /**
     * Method: removeBox
     * Remove the zoombox from the screen and nullify our reference to it.
     */
    removeBox: function() {
        if (this.zoomBox != null) this.map.viewPortDiv.removeChild(this.zoomBox);
        this.zoomBox = null;
    },

    /**
     * Method: activate
     */
    activate: function () {
        if (OpenLayers.Handler.prototype.activate.apply(this, arguments)) {
            this.dragHandler.activate();
            return true;
        } else {
            return false;
        }
    },

    /**
     * Method: deactivate
     */
    deactivate: function () {
        if (OpenLayers.Handler.prototype.deactivate.apply(this, arguments)) {
            this.dragHandler.deactivate();
            return true;
        } else {
            return false;
        }
    },

    /**
     * Method: mouseout
     * Handle mouseout events
     *
     * Parameters:
     * evt - {Event}
     *
     * Returns:
     * {Boolean} Let the event propagate.
     */
    zoommouseout: function (evt) {
        if (this.started && OpenLayers.Util.mouseLeft(evt, this.map.div)) {
            var dragged = (this.start != this.last);
            // this.started = false;
            this.dragging = false;
            // TBD replace with CSS classes
            this.map.div.style.cursor = "";
            this.out(evt);
            this.callback("out", []);
            if (dragged) {
                this.callback("done", [evt.xy]);
            }
            if (document.onselectstart) {
                document.onselectstart = this.oldOnselectstart;
            }
        }
        return true;
    },

    CLASS_NAME: "OpenLayers.Handler.RBox"
});
