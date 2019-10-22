package model.data_structures;

import java.util.NoSuchElementException;

public class ArbolRojoNegro <K extends Comparable<K>, V> implements IArbolBalanceado<K, V>
{
	public static final boolean RED   = true;
    public static final boolean BLACK = false;

    private NodoRojoNegro<K, V> root;

    public ArbolRojoNegro()
    {
    }

    private boolean isRed(NodoRojoNegro<K, V> x) 
    {
        if (x == null) return false;
        return x.color == RED;
    }

    private int size(NodoRojoNegro<K, V> x)
    {
        if (x == null) return 0;
        return x.size;
    } 

    public int size() 
    {
        return size(root);
    }

    public boolean isEmpty() 
    {
        return root == null;
    }

    public V get(K key) 
    {
        if (key == null)
        	throw new IllegalArgumentException("argument to get() is null");
        return get(root, key);
    }

    private V get(NodoRojoNegro<K, V> x, K key) 
    {
        while (x != null)
        {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null;
    }

    public boolean contains(K key) 
    {
        return get(key) != null;
    }

    public void put(K key, V val) 
    {
        if (key == null)
        	throw new IllegalArgumentException("first argument to put() is null");
        if (val == null)
        {
            delete(key);
            return;
        }

        root = put(root, key, val);
        root.color = BLACK;
    }

    private NodoRojoNegro<K, V> put(NodoRojoNegro<K, V> h, K key, V val) 
    { 
        if (h == null)
        	return new NodoRojoNegro<K, V>(key, val, RED, 1);

        int cmp = key.compareTo(h.key);
        if(cmp < 0)
        	h.left  = put(h.left,  key, val); 
        else if(cmp > 0) 
        	h.right = put(h.right, key, val); 
        else              
        	h.val   = val;

        if(isRed(h.right) && !isRed(h.left))      
        	h = rotateLeft(h);
        if(isRed(h.left)  &&  isRed(h.left.left)) 
        	h = rotateRight(h);
        if(isRed(h.left)  &&  isRed(h.right))     
        	flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }

   /***************************************************************************
    *  Red-black tree deletion.
    ***************************************************************************/

    /**
     * Removes the smallest key and associated value from the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() 
    {
        if (isEmpty()) 
        	throw new NoSuchElementException("BST underflow");

        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty())
        	root.color = BLACK;
    }

    private NodoRojoNegro<K, V> deleteMin(NodoRojoNegro<K, V> h)
    { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }

    public void deleteMax()
    {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private NodoRojoNegro<K, V> deleteMax(NodoRojoNegro<K, V> h)
    { 
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }

    public void delete(K key)
    { 
        if (key == null)
        	throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key))
        	return;

        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty())
        	root.color = BLACK;

    }

    private NodoRojoNegro<K, V> delete(NodoRojoNegro<K, V> h, K key)
    { 
        if (key.compareTo(h.key) < 0) 
        {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else
        {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0)
            {
            	NodoRojoNegro<K, V> x = min(h.right);
                h.key = x.key;
                h.val = x.val;

                h.right = deleteMin(h.right);
            }
            else 
            	h.right = delete(h.right, key);
        }
        return balance(h);
    }

    private NodoRojoNegro<K, V> rotateRight(NodoRojoNegro<K, V> h)
    {
    	NodoRojoNegro<K, V> x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    private NodoRojoNegro<K, V> rotateLeft(NodoRojoNegro<K, V> h)
    {
    	NodoRojoNegro<K, V> x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    private void flipColors(NodoRojoNegro<K, V> h)
    {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private NodoRojoNegro<K, V> moveRedLeft(NodoRojoNegro<K, V> h)
    {
        flipColors(h);
        if (isRed(h.right.left))
        { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    private NodoRojoNegro<K, V> moveRedRight(NodoRojoNegro<K, V> h)
    {
        flipColors(h);
        if (isRed(h.left.left))
        { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private NodoRojoNegro<K, V> balance(NodoRojoNegro<K, V> h) 
    {
        if (isRed(h.right))                     
        	h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) 
        	h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     
        	flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    public int height() 
    {
        return height(root);
    }
    private int height(NodoRojoNegro<K, V> x) 
    {
        if (x == null) 
        	return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    public K min() 
    {
        if (isEmpty()) 
        	throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    } 

    private NodoRojoNegro<K, V> min(NodoRojoNegro<K, V> x)
    { 
        if (x.left == null) 
        	return x; 
        else                
        	return min(x.left); 
    } 

    public K max()
    {
        if (isEmpty())
        	throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    } 

    private NodoRojoNegro<K, V> max(NodoRojoNegro<K, V> x)
    { 
        if (x.right == null)
        	return x; 
        else                
        	return max(x.right); 
    } 

    public K floor(K key)
    {
        if (key == null) 
        	throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty())
        	throw new NoSuchElementException("calls floor() with empty symbol table");
        NodoRojoNegro<K, V> x = floor(root, key);
        if (x == null) 
        	return null;
        else          
        	return x.key;
    }    

    private NodoRojoNegro<K, V> floor(NodoRojoNegro<K, V> x, K key) 
    {
        if (x == null) 
        	return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
        	return x;
        if (cmp < 0) 
        	return floor(x.left, key);
        NodoRojoNegro<K, V> t = floor(x.right, key);
        if (t != null)
        	return t; 
        else          
        	return x;
    }

    public K ceiling(K key) 
    {
        if (key == null)
        	throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty())
        	throw new NoSuchElementException("calls ceiling() with empty symbol table");
        NodoRojoNegro<K, V> x = ceiling(root, key);
        if (x == null)
        	return null;
        else           
        	return x.key;  
    }

    private NodoRojoNegro<K, V> ceiling(NodoRojoNegro<K, V> x, K key) 
    {  
        if (x == null)
        	return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
        	return x;
        if (cmp > 0) 
        	return ceiling(x.right, key);
        NodoRojoNegro<K, V> t = ceiling(x.left, key);
        if (t != null)
        	return t; 
        else       
        	return x;
    }

    public K select(int k) 
    {
        if (k < 0 || k >= size()) 
        {
            throw new IllegalArgumentException("argument to select() is invalid: " + k);
        }
        NodoRojoNegro<K, V> x = select(root, k);
        return x.key;
    }

    private NodoRojoNegro<K, V> select(NodoRojoNegro<K, V> x, int k) 
    {
        int t = size(x.left); 
        if      (t > k)
        	return select(x.left,  k); 
        else if (t < k)
        	return select(x.right, k-t-1); 
        else           
        	return x; 
    } 

    public int rank(K key) 
    {
        if (key == null)
        	throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    } 

    private int rank(K key, NodoRojoNegro<K, V> x)
    {
        if (x == null) return 0; 
        int cmp = key.compareTo(x.key); 
        if(cmp < 0) 
        	return rank(key, x.left); 
        else if (cmp > 0)
        	return 1 + size(x.left) + rank(key, x.right); 
        else              
        	return size(x.left); 
    } 

    public Iterable<K> keys()
    {
        if (isEmpty()) 
        	return new ListaSencillamenteEncadenada<K>();
        return keys(min(), max());
    }

    public Iterable<K> keys(K lo, K hi)
    {
        if (lo == null) 
        	throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) 
        	throw new IllegalArgumentException("second argument to keys() is null");

        ListaSencillamenteEncadenada<K> lista = new ListaSencillamenteEncadenada<K>();

        keys(root, lista, lo, hi);
        return lista;
    } 

    private void keys(NodoRojoNegro<K, V> x, ListaSencillamenteEncadenada<K> lista, K lo, K hi) { 
        if (x == null) return; 
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) 
        	keys(x.left, lista, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) 
        	lista.addLast(x.key); 
        if (cmphi > 0)
        	keys(x.right, lista, lo, hi); 
    } 

    public int size(K lo, K hi)
    {
        if (lo == null) 
        	throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null)
        	throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0)
        	return 0;
        if (contains(hi))
        	return rank(hi) - rank(lo) + 1;
        else             
        	return rank(hi) - rank(lo);
    }

    private boolean check() 
    {
        if (!isBST())    
        	System.out.println("Not in symmetric order");
        if (!isSizeConsistent()) 
        	System.out.println("Subtree counts not consistent");
        if (!isRankConsistent())
        	System.out.println("Ranks not consistent");
        if (!is23())            
        	System.out.println("Not a 2-3 tree");
        if (!isBalanced())     
        	System.out.println("Not balanced");
        return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
    }

    private boolean isBST() 
    {
        return isBST(root, null, null);
    }

    private boolean isBST(NodoRojoNegro<K, V> x, K min, K max) 
    {
        if (x == null)
        	return true;
        if (min != null && x.key.compareTo(min) <= 0) 
        	return false;
        if (max != null && x.key.compareTo(max) >= 0)
        	return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    } 

    private boolean isSizeConsistent() 
    { 
    	return isSizeConsistent(root); 
    }
    private boolean isSizeConsistent(NodoRojoNegro<K, V> x)
    {
        if (x == null) return true;
        if (x.size != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    } 

    private boolean isRankConsistent() 
    {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i)))
            	return false;
        for (K key : keys())
            if (key.compareTo(select(rank(key))) != 0)
            	return false;
        return true;
    }

    private boolean is23()
    { 
    	return is23(root);
    }
    private boolean is23(NodoRojoNegro<K, V> x) 
    {
        if (x == null)
        	return true;
        if (isRed(x.right))
        	return false;
        if (x != root && isRed(x) && isRed(x.left))
            return false;
        return is23(x.left) && is23(x.right);
    } 

    private boolean isBalanced() 
    { 
        int black = 0;
        NodoRojoNegro<K, V> x = root;
        while (x != null)
        {
            if (!isRed(x))
            	black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }

    private boolean isBalanced(NodoRojoNegro<K, V> x, int black)
    {
        if (x == null)
        	return black == 0;
        if (!isRed(x))
        	black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    } 
    
}

