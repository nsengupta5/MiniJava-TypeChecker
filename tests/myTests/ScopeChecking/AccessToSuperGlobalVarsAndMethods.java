class Foo {
	public static void main(String[] a) {
		System.out.println(new Map().setIndexPlusOne(0));
	} 
}

class Bar {
	int index;

	public int setIndexPlusOne(int num) {
		index = num + 1;
		return index;
	}
}

class Map extends Bar {

}
