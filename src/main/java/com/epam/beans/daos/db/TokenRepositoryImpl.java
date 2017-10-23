package com.epam.beans.daos.db;

import com.epam.beans.daos.AbstractDAO;
import com.epam.beans.models.PersistentLogin;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository("tokenRepositoryDao")
@Transactional
public class TokenRepositoryImpl extends AbstractDAO implements PersistentTokenRepository{

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setLogin(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLast_used(token.getDate());
        getCurrentSession().persist(persistentLogin);
    }

    @Override
    public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
        PersistentLogin persistentLogin = getCurrentSession().get(PersistentLogin.class, seriesId);
        persistentLogin.setToken(tokenValue);
        persistentLogin.setLast_used(lastUsed);
        getCurrentSession().update(persistentLogin);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {
        try {
            Criteria crit = createBlankCriteria(PersistentLogin.class);
            crit.add(Restrictions.eq("series", series));
            PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();
            return new PersistentRememberMeToken(persistentLogin.getLogin(), persistentLogin.getSeries(),
                    persistentLogin.getToken(), persistentLogin.getLast_used());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void removeUserTokens(String login) {
        Criteria crit = createBlankCriteria(PersistentLogin.class);
        crit.add(Restrictions.eq("login", login));
        PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();
        if (persistentLogin != null) {
            getCurrentSession().delete(persistentLogin);
        }
    }
}
