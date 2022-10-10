class Foo {
	public static void main(String[] a) {
		System.out.println(0);
	} 
}

class Bar {
	public int f(int num) {
		return num + 1;
	}
}

class Lap {
	public int h() {
		return 0;
	}
}

class Map extends Bar {
	int x;
	public int g() {
		int y;
		y = x.f(5);
		return 0;
	}

	public int h() {
		int y;
		y = new Lap().f(5);
		return 0;
	}

	public int j() {
		int y;
		y = new Bar().f(4, 5);
		return 0;
	}

	public int o() {
		int y;
		y = new Bar().f(false);
		return 0;
	}
}


