package org.springframework.data.repository.config;

import static org.springframework.util.StringUtils.*;

import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.w3c.dom.Element;


/**
 * @author Oliver Gierke
 */
public class ManualRepositoryConfigInformation<T extends CommonRepositoryConfigInformation>
        extends ParentDelegatingRepositoryConfigInformation<T> {

    private static final String CUSTOM_IMPL_REF = "custom-impl-ref";

    private Element element;


    /**
     * @param parent
     */
    public ManualRepositoryConfigInformation(Element element, T parent) {

        super(parent);
        this.element = element;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.jpa.repository.config.RepositoryInformation#
     * getBeanName()
     */
    public String getBeanId() {

        return element.getAttribute("id");
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.jpa.repository.config.RepositoryInformation#
     * getInterfaceName()
     */
    public String getInterfaceName() {

        return getBasePackage() + "." + capitalize(getBeanId());
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.jpa.repository.config.RepositoryInformation#
     * getCustomImplementationRef()
     */
    @Override
    public String getCustomImplementationRef() {

        return element.getAttribute(CUSTOM_IMPL_REF);
    }


    /**
     * Returns if a custom implementation shall be autodetected.
     * 
     * @return
     */
    @Override
    public boolean autodetectCustomImplementation() {

        return !hasText(getCustomImplementationRef());
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.jpa.repository.config.AbstractRepositoryInformation
     * #getRepositoryImplementationSuffix()
     */
    @Override
    public String getRepositoryImplementationSuffix() {

        String value =
                element.getAttribute(RepositoryConfig.REPOSITORY_IMPL_POSTFIX);
        return hasText(value) ? value : getParent()
                .getRepositoryImplementationSuffix();
    }


    @Override
    public String getTransactionManagerRef() {

        return getAttribute(RepositoryConfig.TRANSACTION_MANAGER_REF);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.jpa.repository.config.CommonRepositoryInformation
     * #getSource()
     */
    @Override
    public Element getSource() {

        return element;
    }


    /**
     * Returns the attribute of the current context. If it's not set the method
     * will fall back to the parent's source.
     * 
     * @param attribute
     * @return
     */
    protected String getAttribute(String attribute) {

        String value = getSource().getAttribute(attribute);
        return hasText(value) ? value : getParent().getSource().getAttribute(
                attribute);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.jpa.repository.config.CommonRepositoryInformation
     * #getRepositoryFactoryClassName()
     */
    @Override
    public String getRepositoryFactoryBeanClassName() {

        String value =
                element.getAttribute(RepositoryConfig.REPOSITORY_FACTORY_CLASS_NAME);
        return hasText(value) ? value : getParent()
                .getRepositoryFactoryBeanClassName();
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.jpa.repository.config.CommonRepositoryInformation
     * #getQueryLookupStrategyKey()
     */
    @Override
    public Key getQueryLookupStrategyKey() {

        return Key
                .create(getAttribute(RepositoryConfig.QUERY_LOOKUP_STRATEGY));
    }
}