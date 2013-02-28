/* Copyright (c) 2006-2008 MetaCarta, Inc., published under the Clear BSD
 * license.  See http://svn.openlayers.org/trunk/openlayers/license.txt for the
 * full text of the license. */

/**
 * @requires OpenLayers/Handler.js
 */

/**
 * Class: OpenLayers.Handler.Drag
 * The drag handler is used to deal with sequences of browser events related
 *     to dragging.  The handler is used by controls that want to know when
 *     a drag sequence begins, when a drag is happening, and when it has
 *     finished.
 *
 * Controls that use the drag handler typically construct it with callbacks
 *     for 'down', 'move', and 'done'.  Callbacks for these keys are called
 *     when the drag begins, with each move, and when the drag is done.  In
 *     addition, controls can have callbacks keyed to 'up' and 'out' if they
 *     care to differentiate between the types of events that correspond with
 *     the end of a drag sequence.  If no drag actually occurs (no mouse move)
 *     the 'down' and 'up' callbacks will be called, but not the 'done'
 *     callback.
 *
 * Create a new drag handler with the <OpenLayers.Handler.Drag> constructor.
 *
 * Inherits from:
 *  - <OpenLayers.Handler>
 */
OpenLayers.Handler.RDrag = OpenLayers.Class(OpenLayers.Handler.Drag, {

    isLeftClickButt : null,

    initialize: function() {
        OpenLayers.Handler.Drag.prototype.initialize.apply(this, arguments);
    },

    /**
     * Method: mousedown
     * Handle mousedown events
     *
     * Parameters:
     * evt - {Event}
     *
     * Returns:
     * {Boolean} Let the event propagate.
     */
    mousedown: function (evt) {
        var propagate = true;
        this.dragging = false;
        if (this.checkModifiers(evt) && this.getMouseClick(evt)) {
            this.started = true;
            this.start = evt.xy;
            this.last = evt.xy;
            // TBD replace with CSS classes
            this.map.div.style.cursor = "move";
            this.down(evt);
            this.callback("down", [evt.xy]);
            OpenLayers.Event.stop(evt);

            if (!this.oldOnselectstart) {
                this.oldOnselectstart = (document.onselectstart) ? document.onselectstart : function() {
                    return true;
                };
                document.onselectstart = function() {
                    return false;
                };
            }

            propagate = !this.stopDown;
        } else {
            this.started = false;
            this.start = null;
            this.last = null;
        }
        return propagate;
    },

    getMouseClick:function(evt) {
        if (this.isLeftClickButt == null) {
            return true;
        }
        if (this.isLeftClickButt) {
            return OpenLayers.Event.isLeftClick(evt);
        } else {
            return !OpenLayers.Event.isLeftClick(evt);
        }
    },

    CLASS_NAME: "OpenLayers.Handler.RDrag"
});
