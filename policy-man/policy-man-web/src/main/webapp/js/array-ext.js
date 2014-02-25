
Array.prototype.each = function(func){
	for(var i = 0; i < this.length; i++){
		func(this[i]);
	}
}

Array.prototype.eachWithIndex = function(func){
	for(var i = 0; i < this.length; i++){
		func(this[i], i);
	}
}
Array.prototype.contains = function(ele){
	for(var i = 0; i < this.length; i++){
		if(this[i] == ele){
			return true;
		}
	}
	return false;
}

Array.prototype.collect = function(func){
	var result = [];
	for(var i = 0; i < this.length; i++){
		result.push(func(this[i]));
	}
	return result;
}

Array.prototype.filter = function(func){
	var results = [];
	for(var i = 0; i < this.length; i++){
		if(func(this[i])){
			results.push(this[i]);
		}
	}
	return results;
}

Array.prototype.filterWithIndex = function(func){
	var results = [];
	for(var i = 0; i < this.length; i++){
		if(func(this[i], i)){
			results.push(this[i]);
		}
	}
	return results;
}

Array.prototype.foldLeft = function(initialValue, func){
	var result = initialValue;
	for(var i = 0; i < this.length; i++){
		result = func(result, this[i]);
	}
	return result;
}

Array.prototype.findBy = function(propertyName, propertyValue){
	for(var i = 0; i < this.length; i++){
		if(this[i][propertyName] == propertyValue){
			return this[i];
		}
	}
	return null;
}
Array.prototype.removeAt = function(index){
	return this.filterWithIndex(function(ele, i){
		return i != index;
	});
}

function Model(name){
	this.name = name;
	this.params = {
			contentType: "application/json",
			async: false,
	};
}

Model.prototype.all = function(){
	var models = [];
	var newParams = {
			url: this.name+"/all",
			type: "GET",
			success: function(data, status, xhr){
				models = data.all;
			}
	};
	$.ajax($.extend(newParams, this.params));
	return models;
}
Model.prototype.find = function(id){
	var model = {};
	var newParams = {
			url: this.name+"/find/"+id,
			type: "GET",
			success: function(data, status, xhr){
				model = $.extend({}, data);
			}
	};
	$.ajax($.extend(newParams, this.params));
	return model;
}

var Policy = $.extend({}, new Model("policy"));
var Layout = $.extend({}, new Model("layout"));
var Media = $.extend({}, new Model("media"));
function PlayList(area){
	this.startTime = "";
	this.endTime = "";
	this.type = "loop";
	this.content = [];
	this.areaId = area.id;
	this.content = [];
}

PlayList.prototype.html = function(){
	
	
}