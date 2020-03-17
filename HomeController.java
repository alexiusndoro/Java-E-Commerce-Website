package com.fdmgroup.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.daos.BankAccountDAO;
import com.fdmgroup.daos.CustomerDAO;
import com.fdmgroup.daos.OrdersDAO;
import com.fdmgroup.daos.ProductDAO;
import com.fdmgroup.daos.ShipmentDAO;
import com.fdmgroup.entities.BankAccount;
import com.fdmgroup.entities.Customer;
import com.fdmgroup.entities.Order;
import com.fdmgroup.entities.Product;
import com.fdmgroup.entities.Shipment;

@Controller
public class HomeController {

	private HashMap<Product, Integer> basket = new HashMap<Product, Integer>();

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("finalsoloproject");
	private EntityManager em = emf.createEntityManager();

	@RequestMapping(value = "/")
	public String loginPageHandler(Model model, HttpSession session) {
		session.invalidate();
		model.addAttribute("customer", new Customer());
		return "login";
	}

	@RequestMapping(value = "logout")
	public String logoutPageHandler(Model model, HttpSession session) {
		basket.clear();
		session.invalidate();
		model.addAttribute("customer", new Customer());
		return "login";
	}

	@RequestMapping(value = "submitLogin")
	public String loginSubmitHandler(Model model, Customer customer, HttpServletRequest request) {
		model.addAttribute("customer", customer);

		String username = customer.getUsername();
		String password = customer.getPassword();

		CustomerDAO customerDAO = new CustomerDAO(em);

		Customer customerInDB = customerDAO.findCustomer(username);

		// usernot on database
		if (customerInDB == null) {
			return "login"; // model add attribute new user
		}

		String passwordInDB = customerInDB.getPassword();
		// password wrong
		if (!password.equals(passwordInDB)) {
			// model add attribute new user
			return "login";
		}
		// username and password correct:welcome to the site
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		return "loginSubmitted";

	}

	@RequestMapping(value = "blog-home-1")
	public String blogHandler(Model model) {
		model.addAttribute("customer", new Customer());
		return "blog-home-1";
	}

	@RequestMapping(value = "register")
	public String registerPageHandler(Model model) {
		model.addAttribute("customer", new Customer());
		return "register";
	}

	@RequestMapping(value = "submitRegister")
	public String registrationSubmittedHandler(Model model, Customer customer, HttpServletRequest request) {
		model.addAttribute("customer", customer);
		String username = customer.getUsername();
		CustomerDAO customerDAO = new CustomerDAO(em);
		Customer customerInDB = customerDAO.findCustomer(username);

		if (!customer.getConfirmPassword().equals(customer.getPassword())) {
			model.addAttribute("errorMessage", "Error: Passwords do not message");
			return "register";

		}
		if (customerInDB == null) {
			customerDAO.addCustomer(customer);

			HttpSession session = request.getSession();
			session.setAttribute("username", username);

			return "registerSuccess";

		}
		model.addAttribute("errorMessage", "Error: Username is already taken");
		return "register";
	}

	@RequestMapping(value = "listTheProducts")
	public String listTheProducts(Model model) {
		ProductDAO productDAO = new ProductDAO(em);
		List<Product> listOfProducts = productDAO.listAllProducts();
		model.addAttribute("listOfProducts", listOfProducts);
		return "listOfProductsPage";
	}

	@RequestMapping(value = "listTheCustomers")
	public String listTheCustomers(Model model) {
		CustomerDAO customerDAO = new CustomerDAO(em);
		List<Customer> listOfCustomers = customerDAO.listAllCustomers();
		model.addAttribute("listOfCustomers", listOfCustomers);
		return "listOfCustomersPage";

	}

	@RequestMapping(value = "listTheOrders")
	public String listTheOrders(Model model) {
		OrdersDAO ordersDAO = new OrdersDAO(em);
		List<Order> listOfOrders = ordersDAO.listAllOrders();
		model.addAttribute("listOfOrders", listOfOrders);
		return "listOfOrdersPage";

	}

	@RequestMapping(value = "listOrdersForCustomer")
	public String listOrdersForCustomer(Model model, HttpSession session) {
		String username = (String) session.getAttribute("username");
		OrdersDAO ordersDAO = new OrdersDAO(em);
		List<Order> listOfOrders = ordersDAO.listAllOrdersForGivenCustomer(username);
		model.addAttribute("listOfOrders", listOfOrders);
		return "listOfOrdersPage";

	}

	@RequestMapping(value = "listTheShipments")
	public String listTheShipments(Model model) {
		ShipmentDAO shipmentDAO = new ShipmentDAO(em);
		List<Shipment> listOfShipments = shipmentDAO.listAllShipments();
		model.addAttribute("listOfShipments", listOfShipments);
		return "listOfShipmentsPage";

	}

	@RequestMapping(value = "listTheAccounts")
	public String listTheAccounts(Model model) {
		BankAccountDAO accountDAO = new BankAccountDAO(em);
		List<BankAccount> listOfAccounts = accountDAO.listAllAccounts();
		model.addAttribute("listOfAccounts", listOfAccounts);
		model.addAttribute("bankAccount", new BankAccount());
		return "listOfAccountsPage";

	}

	@RequestMapping(value = "listAccountsForUser")
	public String listTheAccountsForUser(Model model, HttpSession session) {
		BankAccountDAO accountDAO = new BankAccountDAO(em);
		String username = (String) session.getAttribute("username");
		List<BankAccount> listOfAccounts = accountDAO.listAllAccountsForGivenUser(username);
		model.addAttribute("listOfAccounts", listOfAccounts);
		model.addAttribute("bankAccount", new BankAccount());
		return "listOfAccountsPage";
	}

	@RequestMapping(value = "customerDetails")
	public String showUserDetails(Model model, HttpSession session) {
		String username = (String) session.getAttribute("username");
		CustomerDAO customerDAO = new CustomerDAO(em);
		Customer customer = customerDAO.findCustomer(username);
		model.addAttribute("customer", customer);
		return "CustomerDetailsPage";
	}

	@RequestMapping(value = "addProductToBasket/{productId}", method = POST)
	public String addProduct(@PathVariable int productId, Model model) {
		ProductDAO productDAO = new ProductDAO(em);
		Product product = productDAO.findProductbyId(productId);
		if (basket.containsKey(product)) {
			int quantity = basket.get(product);
			basket.put(product, quantity + 1);
			double totalPrice = basketTotal();
			model.addAttribute("totalPrice", totalPrice);

		} else
			basket.put(product, 1);
		System.out.println(basket);
		model.addAttribute("basket", basket);
		return "redirect:/basket";
	}

	@RequestMapping(value = "removeFromBasket/{productId}")
	public String removeProduct(@PathVariable int productId, Model model) {
		ProductDAO productDAO = new ProductDAO(em);
		Product product = productDAO.findProductbyId(productId);
		if (basket.containsKey(product)) {
			int quantity = basket.get(product);
			if (quantity > 1) {
				basket.put(product, quantity - 1);
				double totalPrice = basketTotal();
				model.addAttribute("totalPrice", totalPrice);
			} else {
				basket.remove(product);
			}

		}
		model.addAttribute("basket", basket);
		return "redirect:/basket";
	}

	@RequestMapping("basket")
	public String showBasket(Model model) {
		model.addAttribute("basket", basket);
		double totalPrice = basketTotal();
		model.addAttribute("totalPrice", totalPrice);
		return "basket";
	}

	public double basketTotal() {
		double totalPrice = 0.0;
		for (Map.Entry<Product, Integer> eachEntry : basket.entrySet()) {

			int quantity = eachEntry.getValue();
			BigDecimal price = eachEntry.getKey().getPrice();
			totalPrice += price.doubleValue() * quantity;

		}
		return totalPrice;
	}

	@RequestMapping(value = "addOrder", method = POST)
	public String addOrder(Model model, HttpSession session) {
		String username = (String) session.getAttribute("username");

		model.addAttribute("basket", basket);
		double totalPrice = basketTotal();
		model.addAttribute("totalPrice", totalPrice);

		BankAccountDAO accountDAO = new BankAccountDAO(em);
		List<BankAccount> allCustomerAccounts = accountDAO.listAllAccountsForGivenUser(username);

		if (allCustomerAccounts.size() == 0) {
			model.addAttribute("message", "Error: You must have a bank account to be able place orders.");
			return "basket";
		}

		if (allCustomerAccounts.size() == 1) {
			double orderTotal = basketTotal();
			BankAccount account = allCustomerAccounts.get(0);
			int accountNumber = account.getAccountId();
			double accountBalance = account.getBalance();

			if (accountBalance >= orderTotal) {

				accountDAO.updateBalance(accountNumber, orderTotal);
				model.addAttribute("message", "Your order has been placed");

				double newAccountBalance = accountBalance - orderTotal;
				account.setBalance(newAccountBalance);
				model.addAttribute("newAccountBalance", newAccountBalance);

				CustomerDAO customerDAO = new CustomerDAO(em);
				Customer customer = customerDAO.findCustomer(username);

				Order order = new Order();
				order.setCustomer(customer);
				order.setOrderDate(new Date());

				Shipment shipment = new Shipment();
				shipment.setOrder(order);
				shipment.setShipmentDate(new Date());
				order.setShipment(shipment);

				OrdersDAO orderDAO = new OrdersDAO(em);
				orderDAO.addOrder(order);

				basket.clear();
				return "orderConfirmed";
			}

			else {
				model.addAttribute("message", "Insufficient funds to place the order");
				return "basket";
			}

		}

		model.addAttribute("message", "Error: Order cannot be placed, you have too many accounts");
		return "basket";
	}

	@RequestMapping(value = "createAccount", method = POST)
	public String createAccount(Model model, HttpSession session) {

		BankAccountDAO accountDAO = new BankAccountDAO(em);

		String username = (String) session.getAttribute("username");

		List<BankAccount> listOfAccounts = accountDAO.listAllAccountsForGivenUser(username);

		if (listOfAccounts.size() == 0) {
			CustomerDAO cd = new CustomerDAO(em);
			Customer customer = cd.findCustomer(username);

			BankAccount account = new BankAccount();
			account.setCustomer(customer);
			account.setBalance(0.0);

			accountDAO.addAccount(account);

			model.addAttribute("message", "Your account has been added");
			return "redirect:/listAccountsForUser";

		} else {
			model.addAttribute("message", "You already have an account");
		}

		model.addAttribute("listOfAccounts", listOfAccounts);
		model.addAttribute("bankAccount", new BankAccount());
		return "listOfAccountsPage";
	}

	@RequestMapping(value = "updateAccount/{accountId}")
	public String updateAccount(Model model, BankAccount bankAccount, HttpServletRequest request,
			@PathVariable int accountId) {

		double amount = bankAccount.getBalance();

		BankAccountDAO accountDAO = new BankAccountDAO(em);
		BankAccount accountInDB = accountDAO.findAccountByAccountId(bankAccount.getAccountId());

		double balanceInDB = accountInDB.getBalance();

		if (balanceInDB < -amount) {
			model.addAttribute("error", "You cannot withdraw an amount greater that your balance");
			return "redirect:/listAccountsForUser";

		}

		double newBalance = balanceInDB + amount;

		accountDAO.updateBalance(bankAccount.getAccountId(), amount);
		bankAccount.setBalance(newBalance);
		model.addAttribute(bankAccount);

		return "redirect:/listAccountsForUser";
	}

	@RequestMapping(value = "removeAccount/{accountId}")
	public String removeAccount(Model model, BankAccount bankAccount, HttpServletRequest request,
			@PathVariable int accountId) {

		BankAccountDAO accountDAO = new BankAccountDAO(em);

		accountDAO.deleteAccount(accountId);
		return "redirect:/listAccountsForUser";
	}

	@RequestMapping(value = "searchInput")
	public String searchItems(Model model, @RequestParam(value = "search") String name) {

		ProductDAO productDAO = new ProductDAO(em);
		List<Product> allProducts = productDAO.listAllProducts();
		List<Product> listOfProducts = new ArrayList<Product>();

		name = name.toLowerCase();
		for (Product product : allProducts) {

			String productName = product.getName();
			String nameLowerCase = productName.toLowerCase();

			if (nameLowerCase.contains(name)) {
				listOfProducts.add(product);

			}

		}
		model.addAttribute("listOfProducts", listOfProducts);
		return "listOfProductsPage";

	}

}
