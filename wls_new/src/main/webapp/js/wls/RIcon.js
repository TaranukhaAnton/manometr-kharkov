OpenLayers.RIcon = OpenLayers.Class(OpenLayers.Icon, {

  textDiv: null,

  text: null,

  initialize:function(url, size, offset, calculateOffset, text) {
    var options = [url, size, offset, calculateOffset];

    this.textDiv = OpenLayers.Util.createDiv(null, null, null, null, null, null, null, null);

    this.text = text;
    this.textDiv.innerHTML = "<span><NOBR>" + this.text + "</NOBR></span>";

    this.textDiv.style.backgroundColor = "#FFFF99";
    this.textDiv.style.border = "1px solid #C5DCFA";
    this.textDiv.noWrap = true;

    this.url = url;
    this.offset = offset ? offset : new OpenLayers.Pixel(-10, -10);
    this.calculateOffset = calculateOffset;

    var id = OpenLayers.Util.createUniqueID("OL_Icon_");
    this.imageDiv = document.createElement("div");
    this.imageDiv.style.position = "absolute";

    this.img = document.createElement("img");
    this.img.style.position = "relative";
    this.img.src = url;
    if (size != null) {
        this.img.style.width = size.w;
        this.img.style.height = size.h;
    }
    this.img.style.float = "left";

    this.textDiv.style.position = "relative";
    this.textDiv.style.float = "right";
    this.textDiv.style.left = this.img.width + "px";
    //this.textDiv.style.top = -this.img.height/2 + "px";


    this.imageDiv.appendChild(this.textDiv);
    this.imageDiv.appendChild(this.img);

    this.imageDiv.noWrap = true;
  },

  draw: function(px) {
    this.moveTo(px);
    return this.imageDiv;
  },

  setText : function(text){
      this.text = text;
      this.textDiv.innerHTML = "<span><NOBR>" + this.text + "</NOBR></span>";
  },

  setIconUrl : function(url){
      this.img.src = url;
  },

  /**
   * Method: moveTo
   * move icon to passed in px.
   *
   * Parameters:
   * px - {<OpenLayers.Pixel>}
   */
  moveTo: function (px) {
    //if no px passed in, use stored location
    if (px != null) {
      this.px = px;
    }

    if (this.imageDiv != null) {
      if (this.px == null) {
        this.display(false);
      } else {
        if (this.calculateOffset) {
          this.offset = this.calculateOffset(this.size);
        }
        var offsetPx = this.px.offset(this.offset);
       // var offsetPx = this.px.add(-(this.offset.x/2), -this.offset.y);
        OpenLayers.Util.modifyDOMElement(this.imageDiv, null, offsetPx, null,
            null, null, null, null);
      }
    }
  },

  /**
   * Method: display
   * Hide or show the icon
   *
   * Parameters:
   * display - {Boolean}
   */
  display: function(display) {
    this.imageDiv.style.display = (display) ? "block" : "none";
    //this.container.style.display = (display) ? "" : "none";
    this.textDiv.style.display = (display) ? "" : "none";
  },

  CLASS_NAME: "OpenLayers.RIcon"
})