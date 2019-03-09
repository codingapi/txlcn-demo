package org.txlcn.demo.servicea;

import javax.persistence.EntityManager;

/**
 * Description:
 * Date: 19-3-6 下午1:08
 *
 * @author ujued
 */
public class EntityManagerHolder {

    private static ThreadLocal<EntityManager> local = new ThreadLocal<>();

    public static void setEntityManager(EntityManager entityManager) {
        local.set(entityManager);
    }

    public static EntityManager getEntityManager() {
        return local.get();
    }
}
