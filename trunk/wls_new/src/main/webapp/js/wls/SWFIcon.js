OpenLayers.SWFIcon = OpenLayers.Class(OpenLayers.Icon, {

    textDiv: null,

    text: null,

    initialize:function(url, size, offset, calculateOffset, text) {
        var options = [url, size, offset, calculateOffset];

        this.text = text;
        this.textDiv = OpenLayers.Util.createDiv(null, null, null, null, null, null, null, null);
        this.textDiv.innerHTML = "<span>" + this.text + "</span>";
        this.textDiv.style.backgroundColor = "#FFFF99";
        this.textDiv.style.border = "1px solid #C5DCFA";
        this.textDiv.noWrap = true;

        this.url = url;
        this.size = (size) ? size : new OpenLayers.Size(20, 20);
        this.offset = offset ? offset : new OpenLayers.Pixel(-(this.size.w / 2), -(this.size.h / 2));
        this.calculateOffset = calculateOffset;

        this.id = OpenLayers.Util.createUniqueID("SWF_Icon_");
        this.imageDiv = OpenLayers.Util.createDiv(this.id, null, null, null, "absolute", null, null, null);
        this.imageDiv.style.visibility = 'hidden';
        document.body.appendChild(this.imageDiv);
        this.textDiv.style.position = "relative";
        this.textDiv.style.float = "right";
        this.textDiv.style.left = offset.x + "px";
        this.textDiv.style.top = offset.y + "px";
        this.imageDiv.appendChild(this.textDiv);


        this.swfDivId = OpenLayers.Util.createUniqueID("SWF_div_");
        this.swfDiv = OpenLayers.Util.createDiv(this.swfDivId, null, null, null, "relative", null, null, null);
        this.swfDiv.style.position = "relative";
        this.swfDiv.style.float = "left";
        this.imageDiv.appendChild(this.swfDiv)

        this.so = new SWFObject(url, "movie", size.w, size.h, "8");
        this.so.write(this.swfDivId);



      //  this.imageDiv.appendChild(this.img);

        this.imageDiv.noWrap = true;
    },

    draw: function(px) {
        this.moveTo(px);

        this.imageDiv.style.visibility = "visible";
        return this.imageDiv;
    },

    setText : function(text) {
        this.text = text;
        this.textDiv.innerHTML = "<span>" + this.text + "</span>";
    },

    setIconUrl : function(url) {
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
        this.container.style.display = (display) ? "" : "none";
        this.textDiv.style.display = (display) ? "" : "none";
    },

    CLASS_NAME: "OpenLayers.SWFIcon"
})