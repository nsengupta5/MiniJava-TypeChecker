class Factorial{
    public static void main(String[] a){
		System.out.println(new Fac().ComputeFac(0));
    }
}

class Bac {

}

class Fac {
	int chickens;
	int x;

	public int blah() {
		return x;
	}

	public int ComputeFac(int num){
		int num_aux;
		if (num < 1)
			num_aux = 1 ;
		else
			num_aux = num * (this.ComputeFac(num-1));
		return num_aux ;
	}
}
