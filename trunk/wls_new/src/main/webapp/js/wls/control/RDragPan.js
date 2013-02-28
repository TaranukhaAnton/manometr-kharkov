OpenLayers.Control.RDragPan = OpenLayers.Class(OpenLayers.Control.DragPan, {

  initialize: function(callbacks) {
    OpenLayers.Control.DragPan.prototype.initialize.apply(this, [callbacks]);
  },

  draw: function() {
    this.handler = new OpenLayers.Handler.RDrag(this,
    {"move": this.panMap, "done": this.panMapDone});
  },

  setMouseClickMode:function(isLeft) {
    this.handler.isLeftClickButt = isLeft;
  },

  CLASS_NAME: "OpenLayers.Control.RDragPan"
})