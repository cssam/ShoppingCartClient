package ca.bcit.comp4655.ejb.stateful.client.view;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.NoSuchEJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.bcit.comp4655.ejb.stateful.product.Product;
import ca.bcit.comp4655.ejb.stateful.shoppingcart.ShoppingCart;

public class ShoppingCartClient extends HttpServlet
{
	private static final long serialVersionUID = -8253799860173349157L;
	
	//Using dependency injection to obtain a reference to ShoppingCartBean
	@EJB( mappedName="ShoppingCart" )
	ShoppingCart cart;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		
		Product macbook = new Product( "MB-1", "MacBook", 2500);
		Product iPhone = new Product( "ip-2", "iPhone 5", 750 ); 
		
		System.out.println( "Purchasing a MacBook" );
		cart.purchase( macbook, 1 );
		
		System.out.println( "Purchasing another MacBook" );
		cart.purchase( macbook, 1 );
		
		System.out.println( "Purchasing an iPhone" );
		cart.purchase( iPhone, 1 );
		
		System.out.println ( "\nPrint Cart:" );
		Map<Product, Integer> cartContents = cart.getCartContents();
		
		for ( Product product: cartContents.keySet() )
		{
			System.out.print( product );
			System.out.println ( "\tQuantity: " + cartContents.get( product) );
		}
		
		System.out.println ( "Check out:\n" );
		cart.checkout();
		System.out.println("Should throw an \"Object Not Found Exception\" by invoking on cart after @Remove method");
		try
		{
			cart.getCartContents();
		}
		catch ( NoSuchEJBException e )
		{
			System.out.println("Successfully caught no such object exception.");
		}
	     
	}
}
