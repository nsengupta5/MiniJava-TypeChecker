class Foo {
	public static void main(String[] a) {
		System.out.println(0);	
	}
}

class Bar {

}

class Map {
	int a;

	public int f() {
		int x;
		a = new Foo();
		x = a + a;
		return x;
	}
}
