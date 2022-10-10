class Foo {
	public static void main(String[] a) {
		System.out.println(0);
	} 
}

class Bar {
	int[] x;
	public int f() {
		x = new int[3];
		x[2] = false;
		return 7;
	}
}
