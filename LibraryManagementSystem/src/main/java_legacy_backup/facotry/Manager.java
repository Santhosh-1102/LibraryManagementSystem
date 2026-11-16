package facotry;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Manager {
	private static EntityManagerFactory factory;

	static {
		try {
			factory = Persistence.createEntityManagerFactory("Library");
		} catch (Throwable ex) {
			System.err.println("Initial EntityManagerFactory creation failed." + ex);
			ex.printStackTrace();
		}
	}

	public static EntityManagerFactory getFactory() {
		if (factory == null || !factory.isOpen()) {
			factory = Persistence.createEntityManagerFactory("Library");
		}
		return factory;
	}

	public static EntityManager getEntityManager() {
		return getFactory().createEntityManager();
	}

	public EntityManagerFactory getManager() {
		return getFactory();
	}
}
