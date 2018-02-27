/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Lifecycle.EntryCriteria;
import com.emc.documentum.rest.client.sample.model.Lifecycle.LifecycleState;
import com.emc.documentum.rest.client.sample.model.Lifecycle.Module;
import com.emc.documentum.rest.client.sample.model.Lifecycle.Procedure;

@XmlRootElement(name = "state")
public class JaxbLifecycleState implements LifecycleState {
    private String name;
    private String type;
    private String description;
    private boolean exceptional;
    private String exceptionState;
    private boolean allowAttach;
    private boolean allowSchedule;
    private boolean allowReturnToBase;
    private boolean allowDemote;
    private int no;
    private int index;
    private List<String> returnConditions;
    private EntryCriteria entryCriteria;
    private Procedure userCriteria;
    private Procedure action;
    private Procedure userAction;
    private Procedure userPostAction;
    private Module userCriteriaService;
    private Module userActionService;
    private Module userPostService;
    private Module systemAction;
    private String typeOverrideId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExceptional() {
        return exceptional;
    }

    public void setExceptional(boolean exceptional) {
        this.exceptional = exceptional;
    }

    @XmlElement(name="exception-state")
    public String getExceptionState() {
        return exceptionState;
    }

    public void setExceptionState(String exceptionState) {
        this.exceptionState = exceptionState;
    }

    @XmlElement(name="allow-attach")
    public boolean isAllowAttach() {
        return allowAttach;
    }

    public void setAllowAttach(boolean allowAttach) {
        this.allowAttach = allowAttach;
    }

    @XmlElement(name="allow-schedule")
    public boolean isAllowSchedule() {
        return allowSchedule;
    }

    public void setAllowSchedule(boolean allowSchedule) {
        this.allowSchedule = allowSchedule;
    }

    @XmlElement(name="allow-return-to-base")
    public boolean isAllowReturnToBase() {
        return allowReturnToBase;
    }

    public void setAllowReturnToBase(boolean allowReturnToBase) {
        this.allowReturnToBase = allowReturnToBase;
    }

    @XmlElement(name="allow-demote")
    public boolean isAllowDemote() {
        return allowDemote;
    }

    public void setAllowDemote(boolean allowDemote) {
        this.allowDemote = allowDemote;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @XmlElementWrapper(name="return-conditions")
    @XmlElement(name="return-condition")
    public List<String> getReturnConditions() {
        return returnConditions;
    }

    public void setReturnConditions(List<String> returnConditions) {
        this.returnConditions = returnConditions;
    }

    @XmlElement(name="entry-criteria", type=JaxbEntryCriteria.class)
    public EntryCriteria getEntryCriteria() {
        return entryCriteria;
    }

    public void setEntryCriteria(EntryCriteria entryCriteria) {
        this.entryCriteria = entryCriteria;
    }

    @XmlElement(name="user-criteria", type=JaxbProcedure.class)
    public Procedure getUserCriteria() {
        return userCriteria;
    }

    public void setUserCriteria(Procedure userCriteria) {
        this.userCriteria = userCriteria;
    }

    @XmlElement(type=JaxbProcedure.class)
    public Procedure getAction() {
        return action;
    }

    public void setAction(Procedure action) {
        this.action = action;
    }

    @XmlElement(name="user-action", type=JaxbProcedure.class)
    public Procedure getUserAction() {
        return userAction;
    }

    public void setUserAction(Procedure userAction) {
        this.userAction = userAction;
    }

    @XmlElement(name="user-post-action", type=JaxbProcedure.class)
    public Procedure getUserPostAction() {
        return userPostAction;
    }

    public void setUserPostAction(Procedure userPostAction) {
        this.userPostAction = userPostAction;
    }

    @XmlElement(name="user-criteria-service", type=JaxbModule.class)
    public Module getUserCriteriaService() {
        return userCriteriaService;
    }

    public void setUserCriteriaService(Module userCriteriaService) {
        this.userCriteriaService = userCriteriaService;
    }

    @XmlElement(name="user-action-service", type=JaxbModule.class)
    public Module getUserActionService() {
        return userActionService;
    }

    public void setUserActionService(Module userActionService) {
        this.userActionService = userActionService;
    }

    @XmlElement(name="user-post-service", type=JaxbModule.class)
    public Module getUserPostService() {
        return userPostService;
    }

    public void setUserPostService(Module userPostService) {
        this.userPostService = userPostService;
    }

    @XmlElement(name="system-action", type=JaxbModule.class)
    public Module getSystemAction() {
        return systemAction;
    }

    public void setSystemAction(Module systemAction) {
        this.systemAction = systemAction;
    }

    @XmlElement(name="type-override-id")
    public String getTypeOverrideId() {
        return typeOverrideId;
    }

    public void setTypeOverrideId(String typeOverrideId) {
        this.typeOverrideId = typeOverrideId;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbLifecycleState that = (JaxbLifecycleState) obj;
        return Equals.equal(name, that.name)
                && Equals.equal(description, that.description)
                && Equals.equal(type, that.type)
                && Equals.equal(exceptional, that.exceptional)
                && Equals.equal(exceptionState, that.exceptionState)
                && Equals.equal(allowAttach, that.allowAttach)
                && Equals.equal(allowSchedule, that.allowSchedule)
                && Equals.equal(allowReturnToBase, that.allowReturnToBase)
                && Equals.equal(allowDemote, that.allowDemote)
                && Equals.equal(no, that.no)
                && Equals.equal(index, that.index)
                && Equals.equal(returnConditions, that.returnConditions)
                && Equals.equal(entryCriteria, that.entryCriteria)
                && Equals.equal(userCriteria, that.userCriteria)
                && Equals.equal(action, that.action)
                && Equals.equal(userAction, that.userAction)
                && Equals.equal(userPostAction, that.userPostAction)
                && Equals.equal(userCriteriaService, that.userCriteriaService)
                && Equals.equal(userActionService, that.userActionService)
                && Equals.equal(userPostService, that.userPostService)
                && Equals.equal(systemAction, that.systemAction)
                && Equals.equal(typeOverrideId, that.typeOverrideId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, exceptional, exceptionState, no, entryCriteria);
    }


    public static class JaxbEntryCriteria implements EntryCriteria {
        private String expression;
        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getExpression() {
            return expression;
        }
        public void setExpression(String expression) {
            this.expression = expression;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbEntryCriteria that = (JaxbEntryCriteria) obj;
            return Equals.equal(id, that.id)
                    && Equals.equal(expression, that.expression);
        }
        @Override
        public int hashCode() {
            return Objects.hash(id, expression);
        }
    }
    
    public static class JaxbProcedure implements Procedure {
        private String name;
        private String version;
        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getVersion() {
            return version;
        }
        public void setVersion(String version) {
            this.version = version;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbProcedure that = (JaxbProcedure) obj;
            return Equals.equal(id, that.id)
                    && Equals.equal(name, that.name)
                    && Equals.equal(version, that.version);
        }
        @Override
        public int hashCode() {
            return Objects.hash(id, name, version);
        }
    }
    
    public static class JaxbModule implements Module {
        private String id;
        private String name;
        private String primaryClass;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @XmlElement(name="primary-class")
        public String getPrimaryClass() {
            return primaryClass;
        }
        public void setPrimaryClass(String primaryClass) {
            this.primaryClass = primaryClass;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbModule that = (JaxbModule) obj;
            return Equals.equal(id, that.id)
                    && Equals.equal(name, that.name)
                    && Equals.equal(primaryClass, that.primaryClass);
        }
        @Override
        public int hashCode() {
            return Objects.hash(id, name, primaryClass);
        }
    }
}
