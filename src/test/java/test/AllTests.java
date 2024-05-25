package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ClienteServiceTest.class, ClienteDAOTest.class, 
	ProdutoDAOTest.class, ProdutoServiceTest.class,
	VendaDAOTest.class})
public class AllTests {

}
