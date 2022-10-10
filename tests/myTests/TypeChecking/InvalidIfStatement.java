class Foo {
	public static void main(String[] a) {
		System.out.println(0);
	} 
}

class Bar {
	int[] x;
	public int f() {
		if (x) {
			System.out.println(1);
		}
		else {
			System.out.println(2);
		}
		return 0;
	}
}
