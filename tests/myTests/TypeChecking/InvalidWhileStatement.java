class Foo {
	public static void main(String[] a) {
		System.out.println(0);
	} 
}

class Bar {
	int x;
	public int f() {
		while (x) {
			System.out.println(1);
		}
		return 0;
	}
}
