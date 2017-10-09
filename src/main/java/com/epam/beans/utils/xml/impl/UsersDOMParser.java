package com.epam.beans.utils.xml.impl;

import com.epam.beans.models.User;
import com.epam.beans.models.UserRole;
import com.epam.beans.utils.xml.DOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service("theUsersDomParser")
public class UsersDOMParser implements DOMParser<List<User>> {

    @Override
    public List<User> apply(InputStream in) {
        List<User> result = new ArrayList<>();
        try {
            Document document = DOMParser.buildDocument(in);
            result.add(parseDOM(document));
        } catch (Exception e) {
            throw new RuntimeException("Can't parse input stream from user document by DOM Parser", e);
        }
        return result;
    }


    private User parseDOM(Document document) {
        User user = new User();
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("user");
        user = parseUser(user, nList);
        return user;
    }

    private User parseUser(User user, NodeList nList) {
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                user.setLogin(eElement.getElementsByTagName("login").item(0).getTextContent());
                user.setPassword(eElement.getElementsByTagName("password").item(0).getTextContent());
                user.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                String date = eElement.getElementsByTagName("birthday").item(0).getTextContent();
                user.setBirthday(LocalDate.parse(date));
                user.setEmail(eElement.getElementsByTagName("email").item(0).getTextContent());
                user.setUserRole(eElement.getElementsByTagName("role").item(0).getTextContent());
            }
        }
        return user;
    }
}
