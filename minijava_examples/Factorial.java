class Factorial{
    public static void main(String[] a){
		System.out.println(new Fac().ComputeFac(0));
    }
}

class Fac {
	int chickens;
	public int ComputeFac(int num){
		int num_aux;
		if (num < 1)
			num_aux = 1 ;
		else
			num_aux = num * (this.ComputeFac(num-1));
		return num_aux ;
	}
}
