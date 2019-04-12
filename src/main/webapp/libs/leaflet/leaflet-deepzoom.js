/*
 * Leaflet-DeepZoom 2.0.0
 * Displaying DeepZoom tiles with Leaflet 1.x
 * by Al Farisi, Indokreatif Teknologi
 * https://github.com/alfarisi/leaflet-deepzoom
 */

L.TileLayer.DeepZoom = L.TileLayer.extend({
	options: {
        getTileUrl:'',
		rootMap:'',
		width: -1,
		height: -1,
		imageFormat: 'png',
		tileSize: 256,
		bound:undefined,
		maxZoom: undefined
	},

	initialize: function (url, options) {
		var options = L.setOptions(this, options);
		this._url = url;
		
		if (options.width < 0 || options.height < 0) {
			throw new Error("The user must set the Width and Height of the image");
		}
		
		var imageSize = L.point(options.width, options.height),
			tileSize = options.tileSize;

		this._imageSize = [imageSize];
		this._gridSize = [this._getGridSize(imageSize)];
		
		for (var i=0;i<10;i++) {
			imageSize = imageSize.divideBy(2).floor();
			this._imageSize.push(imageSize);
			this._gridSize.push(this._getGridSize(imageSize));
		}

		this._imageSize.reverse();	
		this._gridSize.reverse();

		options.maxNativeZoom = this._gridSize.length - 1;
 

		
		if (typeof options.maxZoom == 'undefined') {
			options.maxZoom = options.maxNativeZoom;
		}
		
		var maxZoomGrid = this._gridSize[options.maxNativeZoom];
		// var southWest = map.unproject([-10, 10], options.maxNativeZoom);
		// var northEast = map.unproject([options.width, -options.height], options.maxNativeZoom);
		//  options.bounds = new L.LatLngBounds(southWest, northEast);
	},

	_getGridSize: function (imageSize) {
		var tileSize = this.options.tileSize;
		return L.point(Math.ceil(imageSize.x / tileSize), Math.ceil(imageSize.y / tileSize));
	},
	
	_getCustomCoor:function(tilePoint){
		var customY=this.options.height/this.options.tileSize-tilePoint.y;
		var customCoor=L.point(tilePoint.x,customY);
		return customCoor;
	},
	_addTile: function (coords, container) {
		var tilePos = this._getTilePos(coords),
			key = this._tileCoordsToKey(coords);

		var tile = this.createTile(this._wrapCoords(coords), L.bind(this._tileReady, this, coords));
		
		this._initTile(tile);
		
		var imageSize = this._imageSize[this._getZoomForUrl()],
			gridSize = this._gridSize[this._getZoomForUrl()],
			tileSize = this.options.tileSize;
		
		if (coords.x === gridSize.x - 1) {
			tile.style.width = imageSize.x - (tileSize * (gridSize.x - 1)) + 'px';
		} 

		if (coords.y === gridSize.y - 1) {
			tile.style.height = imageSize.y - (tileSize * (gridSize.y - 1)) + 'px';
		}
		
		if (this.createTile.length < 2) {
			L.Util.requestAnimFrame(L.bind(this._tileReady, this, coords, null, tile));
		}

		L.DomUtil.setPosition(tile, tilePos);

		this._tiles[key] = {
			el: tile,
			coords: coords,
			current: true
		};

		container.appendChild(tile);
		this.fire('tileloadstart', {
			tile: tile,
			coords: coords
		});
	},

	getTileUrl: function(tilePoint) {
		if(this.options.getTileUrl!=''){
			return this.options.getTileUrl(tilePoint);
		}

		var y=tilePoint.y*(-1);
		return this._url + tilePoint.z+'/' + tilePoint.x + '_' + y + '.' + this.options.imageFormat;
	}
	
	

});

L.tileLayer.deepzoom = function (url, options) {
	return new L.TileLayer.DeepZoom(url, options);
};