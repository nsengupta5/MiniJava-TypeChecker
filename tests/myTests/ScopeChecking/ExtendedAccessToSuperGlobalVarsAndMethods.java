class Foo {
	public static void main(String[] a) {
		System.out.println(new Lap().setIndexPlusOne(0));
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

class Rap extends Map {

}

class Lap extends Rap {
	public boolean setIndexPlusOne(boolean num) {
		return num;
	}
}
