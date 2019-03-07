package;

import neko.Lib;

using StringTools;

class MyClass {
    @range(1, 8) public var value:Int;
	public function new() {
		
	}
}

class Main 
{
	
	static function main() 
	{
		trace("hello neko");
		Sys.println("sys println");
		for (i in [1, 2, 3]) {
			trace(i);
		}
		var buffer = "";
		function append(s:String) {
			buffer += s;
		}
		append("foo");
		append("bar");
		trace(buffer); // foobar
		trace(haxe.rtti.Meta.getFields(MyClass).value.range); // [1,8]
		var mc = new MyClass();
		mc.value = 15;
		trace(mc.value);
		trace('mc.value = ${mc.value}');
		trace("  te&st  ".trim().htmlEscape());
	}
	
}
