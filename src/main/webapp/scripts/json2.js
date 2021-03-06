if (!this.JSON)
	this.JSON = {};
(function() {
	function k(a) {
		return a < 10 ? "0" + a : a
	}
	function n(a) {
		o.lastIndex = 0;
		return o.test(a) ? '"'
				+ a.replace(o, function(c) {
					var d = q[c];
					return typeof d === "string" ? d : "\\u"
							+ ("0000" + c.charCodeAt(0).toString(16)).slice(-4)
				}) + '"' : '"' + a + '"'
	}
	function l(a, c) {
		var d, f, i = g, e, b = c[a];
		if (b && typeof b === "object" && typeof b.toJSON === "function")
			b = b.toJSON(a);
		if (typeof j === "function")
			b = j.call(c, a, b);
		switch (typeof b) {
		case "string":
			return n(b);
		case "number":
			return isFinite(b) ? String(b) : "null";
		case "boolean":
		case "null":
			return String(b);
		case "object":
			if (!b)
				return "null";
			g += m;
			e = [];
			if (Object.prototype.toString.apply(b) === "[object Array]") {
				f = b.length;
				for (a = 0; a < f; a += 1)
					e[a] = l(a, b) || "null";
				c = e.length === 0 ? "[]" : g ? "[\n" + g + e.join(",\n" + g)
						+ "\n" + i + "]" : "[" + e.join(",") + "]";
				g = i;
				return c
			}
			if (j && typeof j === "object") {
				f = j.length;
				for (a = 0; a < f; a += 1) {
					d = j[a];
					if (typeof d === "string")
						if (c = l(d, b))
							e.push(n(d) + (g ? ": " : ":") + c)
				}
			} else
				for (d in b)
					if (Object.hasOwnProperty.call(b, d))
						if (c = l(d, b))
							e.push(n(d) + (g ? ": " : ":") + c);
			c = e.length === 0 ? "{}" : g ? "{\n" + g + e.join(",\n" + g)
					+ "\n" + i + "}" : "{" + e.join(",") + "}";
			g = i;
			return c
		}
	}
	if (typeof Date.prototype.toJSON !== "function") {
		Date.prototype.toJSON = function() {
			return isFinite(this.valueOf()) ? this.getUTCFullYear() + "-"
					+ k(this.getUTCMonth() + 1) + "-" + k(this.getUTCDate())
					+ "T" + k(this.getUTCHours()) + ":"
					+ k(this.getUTCMinutes()) + ":" + k(this.getUTCSeconds())
					+ "Z" : null
		};
		String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON = function() {
			return this.valueOf()
		}
	}
	var p = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g, o = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g, g, m, q = {
		"\u0008" : "\\b",
		"\t" : "\\t",
		"\n" : "\\n",
		"\u000c" : "\\f",
		"\r" : "\\r",
		'"' : '\\"',
		"\\" : "\\\\"
	}, j;
	if (typeof JSON.stringify !== "function")
		JSON.stringify = function(a, c, d) {
			var f;
			m = g = "";
			if (typeof d === "number")
				for (f = 0; f < d; f += 1)
					m += " ";
			else if (typeof d === "string")
				m = d;
			if ((j = c) && typeof c !== "function"
					&& (typeof c !== "object" || typeof c.length !== "number"))
				throw new Error("JSON.stringify");
			return l("", {
				"" : a
			})
		};
	if (typeof JSON.parse !== "function")
		JSON.parse = function(a, c) {
			function d(f, i) {
				var e, b, h = f[i];
				if (h && typeof h === "object")
					for (e in h)
						if (Object.hasOwnProperty.call(h, e)) {
							b = d(h, e);
							if (b !== undefined)
								h[e] = b;
							else
								delete h[e]
						}
				return c.call(f, i, h)
			}
			p.lastIndex = 0;
			if (p.test(a))
				a = a.replace(p, function(f) {
					return "\\u"
							+ ("0000" + f.charCodeAt(0).toString(16)).slice(-4)
				});
			if (/^[\],:{}\s]*$/
					.test(a
							.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@")
							.replace(
									/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,
									"]").replace(/(?:^|:|,)(?:\s*\[)+/g, ""))) {
				a = eval("(" + a + ")");
				return typeof c === "function" ? d({
					"" : a
				}, "") : a
			}
			throw new SyntaxError("JSON.parse");
		}
})();

function parseAttrRecursion(o, name, value, setFun) {
	// alert(name); //user[i].address[j].name=value
	// user[i].email
	// user.phone
	if (name.split == undefined)
		alert(name);
	var attr = name.split(".");

	if (attr.length == 1) {// likes "name"=value
		// alert("1:" + name);
		// var obj = {};
		if (setFun != undefined) {
			o[name] = setFun(o[name], value);
			// alert(o[name]);
		} else
			o[name] = value;
		// alert(obj);
		return obj;
	} else {// likes "user[i].name"=value
		// alert("2:" + name);
		var tempName = name.replace(attr[0] + ".", "");
		// alert(tempName);
		var properties = attr[0].split(/\[|\]/);
		// alert(properties.length);
		if (properties.length >= 2) {// likes user[i]

			if (o[properties[0]] == undefined) {
				o[properties[0]] = [];
			}
			var obj = o[properties[0]];
			// alert(properties[1]);
			var subobj = obj[parseInt(properties[1])];
			if (subobj == undefined) {
				subobj = {};
				obj[parseInt(properties[1])] = subobj;
			}
			parseAttrRecursion(subobj, tempName, value, setFun);
			return subobj;

		} else {// likes "user.name"=value
			// alert("3:" + name);
			if (o[properties[0]] == undefined) {
				o[properties[0]] = {};
			}
			var subobj = o[properties[0]];
			parseAttrRecursion(subobj, tempName, value, setFun);
			return subobj;
		}

	}

};
function parseAttr(o, name, value, setFun) {

	parseAttrRecursion(o, name, value, setFun);
	return;

	var nameTemp = name;
	var attrs0 = nameTemp.split(".");
	// var attrs0 = [];
	var attrs = name.replace('.', '').split(/\[|\]/);
	if (attrs.length == 3) {// name likes "abroadRelatives[0].birthday"
		// attrs[0]=>abroadRelatives
		// attrs[1]=>0
		// attrs[2]=>birthday

		var obj = [];
		var subobj = {};
		if (o[attrs[0]] != undefined) {
			obj = o[attrs[0]];
			subobj = obj[parseInt(attrs[1])];
			if (subobj == undefined) {
				subobj = {};
			}
		} else {
			o[attrs[0]] = obj;
		}
		obj[parseInt(attrs[1])] = subobj;
		if (setFun != undefined) {
			setFun(subobj[attrs[2]], value);
		} else
			subobj[attrs[2]] = value;
	} else if (attrs.length != 3 && attrs0.length == 2) {
		// name likes "user.name"
		// attrs[0]=>user
		// attrs[1]=>name

		var obj = {};
		if (o[attrs0[0]] != undefined) {
			obj = o[attrs0[0]];
		} else {
			o[attrs0[0]] = obj;
		}

		if (setFun != undefined) {
			setFun(obj, value);
		} else
			obj[attrs0[1]] = value;

	} else {
		if (setFun != undefined) {
			if (o[name] == undefined)
				o[name] = [];
			setFun(o[name], value);
		} else
			o[name] = value;

	}
};

$.fn.serializeObject = function() {
	var o = {};
	// 文本框
	// alert(this);
	$(this).find(":text").each(function(i) {

		parseAttr(o, this.name, this.value);

	});
	// 下拉列表
	$(this).find("select").each(function(i) {

		parseAttr(o, this.name, this.value);

	});
	// 单选框
	this.find(":radio").filter(":checked").each(function(i) {

		parseAttr(o, this.name, this.value);

	});
	// 复选框开始

	this.find(":checkbox").filter(":checked").each(function(i) {

		var setFun = function(obj, value) {

			if (obj) {
				if (!obj.push) {
					obj = [ {
						"id" : obj
					} ];
				}
				obj.push({
					"id" : value || ''
				});
			} else {
				obj = [ {
					"id" : value
				} ];
			}
			return obj;
		};

		parseAttr(o, this.name, this.value, setFun);
		/*
		 * if (o[this.name]) { if (!o[this.name].push) { o[this.name] = [ { "id" :
		 * o[this.name] } ]; } o[this.name].push({ "id" : this.value || '' }); }
		 * else { o[this.name] = [ { "id" : this.value } ]; }
		 */
	});

	return o;
	/*
	 * var temp_cb_arr = temp_cb.split(","); var cb_name = ""; var cb_value =
	 * ""; for ( var temp_cb_i = 0; temp_cb_i < temp_cb_arr.length - 1;
	 * temp_cb_i++) { cb_name = temp_cb_arr[temp_cb_i]; var cb_value_length =
	 * $("input[name='" + temp_cb_arr[temp_cb_i] + "']:checked").length;
	 * $("input[name='" + temp_cb_arr[temp_cb_i] + "']:checked").each(
	 * function(i) { if (i == cb_value_length - 1) cb_value += this.value; else
	 * cb_value += this.value + ",";
	 * 
	 * }); // alert(cb_name); // alert(cb_value); a.push( { name : cb_name,
	 * value : cb_value }); } return a; // 复选框结束 // 组合为JSON var temp_json = "";
	 * for ( var json_i = 0; json_i < a.length; json_i++) { if (json_i !=
	 * a.length - 1) { temp_json += '"' + a[json_i].name + '":"' +
	 * a[json_i].value + '",'; } else { temp_json += '"' + a[json_i].name +
	 * '":"' + a[json_i].value + '"'; } } return "{" + temp_json + "}";
	 * 
	 */
};

$.postJSON = function(url, data, async, success, error) {
	if (url == "") {
		$.ajax({
			'type' : "POST",
			'contentType' : "application/json",
			'data' : data,
			'async' : async,
			'dataType' : "json",
			'error' : error,
			'success' : success

		});
	} else {
		$.ajax({
			'type' : "POST",
			'url' : url,
			'contentType' : "application/json",
			'data' : data,
			'async' : async,
			'dataType' : "json",
			'error' : error,
			'success' : success

		});
	}

};
