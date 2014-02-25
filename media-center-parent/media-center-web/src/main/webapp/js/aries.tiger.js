/**each is a function which accept a {function} parameter, 
 * and apply the {function} on each element of the array.
*/
Array.prototype.each = function(func){
	for(var i =0; i < this.length; i++){
		func(this[i]);
	}
}
/**
 * collect is a function which accept a {function} parameter,
 * and apply the {function} on each element of the array,
 * then return a new array
 */
Array.prototype.collect = function(func){
	var result = [];
	for(var i = 0; i < this.length; i++){
		result.push(func(this[i]));
	}
	return result;
}
/**
 * if contains
 */
Array.prototype.contains = function(element){
	for(var i = 0; i < this.length; i++){
		if(this[i] == element){
			return true;
		}
	}
	return false;
}

/**
 * if strict contains
 */
Array.prototype.strictContains = function(element){
	for(var i = 0; i < this.length; i++){
		if(this[i] === element){
			return true;
		}
	}
	return false;
}
