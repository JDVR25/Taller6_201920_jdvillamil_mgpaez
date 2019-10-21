package model.data_structures;


public class NodoRojoNegro <K extends Comparable<K>, V>
{

	public static final boolean RED   = true;
	public static final boolean BLACK = false;
	private K key;           // key
	private V val;         // associated data
	private NodoRojoNegro<K, V> left;
	private NodoRojoNegro<K, V> right;  // links to left and right subtrees
	private boolean color;     // color of parent link
	private int size;          // subtree count

	public NodoRojoNegro(K key, V val, boolean color, int size) {
		this.key = key;
		this.val = val;
		this.color = color;
		this.size = size;
	}

	// is node x red; false if x is null ?
	public boolean isRed()
	{
		return color == RED;
	}

	// number of node in subtree rooted at x; 0 if x is null
	public int size()
	{
		return size;
	} 

	public V get(K pKey)
	{
		V buscado = null;
			int cmp = pKey.compareTo(key);
			if(cmp < 0)
				buscado = left.get(pKey);
			else if(cmp > 0)
				buscado = right.get(pKey);
			else
				buscado = val;
		return buscado;
	}

	// insert the key-value pair in the subtree rooted at h
	public NodoRojoNegro<K, V> put(K pKey, V pVal) { 

		NodoRojoNegro<K, V> enMiPos = this;
		int cmp = pKey.compareTo(pKey);
		if(cmp < 0)
			left  = put(pKey, pVal); 
		else if(cmp > 0)
			right = put(pKey, pVal); 
		else
			val   = pVal;

		// fix-up any right-leaning links
		if (right.isRed() && !left.isRed())
			enMiPos = rotateLeft();
		if (left.isRed() && left.left.isRed())
			enMiPos = rotateRight();
		if (left.isRed() &&  right.isRed())
			flipColors();
		size = size(left) + size(right) + 1;

		return enMiPos;
	}

	public NodoRojoNegro<K, V> rotateRight(NodoRojoNegro<K, V> h) {
		// assert (h != null) && isRed(h.left);
		NodoRojoNegro<K, V> x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = x.right.color;
		x.right.color = RED;
		x.size = h.size;
		h.size = size(h.left) + size(h.right) + 1;
		return x;
	}

	// make a right-leaning link lean to the left
	public NodoRojoNegro<K, V> rotateLeft(NodoRojoNegro<K, V> h) {
		// assert (h != null) && isRed(h.right);
		NodoRojoNegro<K, V> x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = x.left.color;
		x.left.color = RED;
		x.size = h.size;
		h.size = size(h.left) + size(h.right) + 1;
		return x;
	}

	// flip the colors of a node and its two children
	public void flipColors() {
		// h must have opposite color of its two children
		// assert (h != null) && (h.left != null) && (h.right != null);
		// assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
		//    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
		color = !color;
		left.color = !left.color;
		right.color = !right.color;
	}

	// Assuming that h is red and both h.left and h.left.left
	// are black, make h.left or one of its children red.
	public NodoRojoNegro<K, V> moveRedLeft(NodoRojoNegro<K, V> h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

		flipColors(h);
		if (isRed(h.right.left)) { 
			h.right = rotateRight(h.right);
			h = rotateLeft(h);
			flipColors(h);
		}
		return h;
	}

	// Assuming that h is red and both h.right and h.right.left
	// are black, make h.right or one of its children red.
	public NodoRojoNegro<K, V> moveRedRight(NodoRojoNegro<K, V> h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
		flipColors(h);
		if (isRed(h.left.left)) { 
			h = rotateRight(h);
			flipColors(h);
		}
		return h;
	}

	// restore red-black tree invariant
	public NodoRojoNegro balance(NodoRojoNegro h) {
		// assert (h != null);

		if (isRed(h.right))                      h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
		if (isRed(h.left) && isRed(h.right))     flipColors(h);

		h.size = size(h.left) + size(h.right) + 1;
		return h;
	}

	public int height(NodoRojoNegro x) {
		if (x == null) return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}

	// the smallest key in subtree rooted at x; null if no such key
	public NodoRojoNegro min(NodoRojoNegro x) { 
		// assert x != null;
		if (x.left == null) return x; 
		else                return min(x.left); 
	}

	// the largest key in the subtree rooted at x; null if no such key
	public NodoRojoNegro max(NodoRojoNegro x) { 
		// assert x != null;
		if (x.right == null) return x; 
		else                 return max(x.right); 
	} 

	// the largest key in the subtree rooted at x less than or equal to the given key
	public NodoRojoNegro floor(NodoRojoNegro x, K key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0) return x;
		if (cmp < 0)  return floor(x.left, key);
		NodoRojoNegro t = floor(x.right, key);
		if (t != null) return t; 
		else           return x;
	}

	// the smallest key in the subtree rooted at x greater than or equal to the given key
	public NodoRojoNegro ceiling(NodoRojoNegro x, K key) {  
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0) return x;
		if (cmp > 0)  return ceiling(x.right, key);
		NodoRojoNegro t = ceiling(x.left, key);
		if (t != null) return t; 
		else           return x;
	}

	// the key of rank k in the subtree rooted at x
	public NodoRojoNegro select(NodoRojoNegro x, int k) {
		// assert x != null;
		// assert k >= 0 && k < size(x);
		int t = size(x.left); 
		if      (t > k) return select(x.left,  k); 
		else if (t < k) return select(x.right, k-t-1); 
		else            return x; 
	}

	// number of keys less than key in the subtree rooted at x
	public int rank(K key, NodoRojoNegro x) {
		if (x == null) return 0; 
		int cmp = key.compareTo(x.key); 
		if      (cmp < 0) return rank(key, x.left); 
		else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right); 
		else              return size(x.left); 
	} 

	// add the keys between lo and hi in the subtree rooted at x
	// to the queue
	public void keys(NodoRojoNegro x, ListaSencillamenteEncadenada<K> lista, K lo, K hi) { 
		if (x == null) return; 
		int cmplo = lo.compareTo(x.key); 
		int cmphi = hi.compareTo(x.key); 
		if (cmplo < 0) keys(x.left, lista, lo, hi); 
		if (cmplo <= 0 && cmphi >= 0) lista.addLast(x.key); 
		if (cmphi > 0) keys(x.right, lista, lo, hi); 
	}

	public boolean isBST(NodoRojoNegro x, K min, K max) {
		if (x == null) return true;
		if (min != null && x.key.compareTo(min) <= 0) return false;
		if (max != null && x.key.compareTo(max) >= 0) return false;
		return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
	}

	public boolean isSizeConsistent(NodoRojoNegro x) {
		if (x == null) return true;
		if (x.size != size(x.left) + size(x.right) + 1) return false;
		return isSizeConsistent(x.left) && isSizeConsistent(x.right);
	}

	public boolean is23(NodoRojoNegro x) {
		if (x == null) return true;
		if (isRed(x.right)) return false;
		if (x != root && isRed(x) && isRed(x.left))
			return false;
		return is23(x.left) && is23(x.right);
	} 

	public boolean isBalanced(NodoRojoNegro x, int black) {
		if (x == null) return black == 0;
		if (!isRed(x)) black--;
		return isBalanced(x.left, black) && isBalanced(x.right, black);
	} 
}
