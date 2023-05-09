package com.Chatable.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChatExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSenderIsNull() {
            addCriterion("sender is null");
            return (Criteria) this;
        }

        public Criteria andSenderIsNotNull() {
            addCriterion("sender is not null");
            return (Criteria) this;
        }

        public Criteria andSenderEqualTo(String value) {
            addCriterion("sender =", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotEqualTo(String value) {
            addCriterion("sender <>", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderGreaterThan(String value) {
            addCriterion("sender >", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderGreaterThanOrEqualTo(String value) {
            addCriterion("sender >=", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderLessThan(String value) {
            addCriterion("sender <", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderLessThanOrEqualTo(String value) {
            addCriterion("sender <=", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderLike(String value) {
            addCriterion("sender like", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotLike(String value) {
            addCriterion("sender not like", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderIn(List<String> values) {
            addCriterion("sender in", values, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotIn(List<String> values) {
            addCriterion("sender not in", values, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderBetween(String value1, String value2) {
            addCriterion("sender between", value1, value2, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotBetween(String value1, String value2) {
            addCriterion("sender not between", value1, value2, "sender");
            return (Criteria) this;
        }

        public Criteria andReceiverIsNull() {
            addCriterion("receiver is null");
            return (Criteria) this;
        }

        public Criteria andReceiverIsNotNull() {
            addCriterion("receiver is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverEqualTo(String value) {
            addCriterion("receiver =", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverNotEqualTo(String value) {
            addCriterion("receiver <>", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverGreaterThan(String value) {
            addCriterion("receiver >", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverGreaterThanOrEqualTo(String value) {
            addCriterion("receiver >=", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverLessThan(String value) {
            addCriterion("receiver <", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverLessThanOrEqualTo(String value) {
            addCriterion("receiver <=", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverLike(String value) {
            addCriterion("receiver like", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverNotLike(String value) {
            addCriterion("receiver not like", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverIn(List<String> values) {
            addCriterion("receiver in", values, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverNotIn(List<String> values) {
            addCriterion("receiver not in", values, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverBetween(String value1, String value2) {
            addCriterion("receiver between", value1, value2, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverNotBetween(String value1, String value2) {
            addCriterion("receiver not between", value1, value2, "receiver");
            return (Criteria) this;
        }

        public Criteria andLastTimeIsNull() {
            addCriterion("last_time is null");
            return (Criteria) this;
        }

        public Criteria andLastTimeIsNotNull() {
            addCriterion("last_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastTimeEqualTo(Date value) {
            addCriterion("last_time =", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotEqualTo(Date value) {
            addCriterion("last_time <>", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeGreaterThan(Date value) {
            addCriterion("last_time >", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_time >=", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeLessThan(Date value) {
            addCriterion("last_time <", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_time <=", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeIn(List<Date> values) {
            addCriterion("last_time in", values, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotIn(List<Date> values) {
            addCriterion("last_time not in", values, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeBetween(Date value1, Date value2) {
            addCriterion("last_time between", value1, value2, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_time not between", value1, value2, "lastTime");
            return (Criteria) this;
        }

        public Criteria andSenderShowIsNull() {
            addCriterion("sender_show is null");
            return (Criteria) this;
        }

        public Criteria andSenderShowIsNotNull() {
            addCriterion("sender_show is not null");
            return (Criteria) this;
        }

        public Criteria andSenderShowEqualTo(Integer value) {
            addCriterion("sender_show =", value, "senderShow");
            return (Criteria) this;
        }

        public Criteria andSenderShowNotEqualTo(Integer value) {
            addCriterion("sender_show <>", value, "senderShow");
            return (Criteria) this;
        }

        public Criteria andSenderShowGreaterThan(Integer value) {
            addCriterion("sender_show >", value, "senderShow");
            return (Criteria) this;
        }

        public Criteria andSenderShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("sender_show >=", value, "senderShow");
            return (Criteria) this;
        }

        public Criteria andSenderShowLessThan(Integer value) {
            addCriterion("sender_show <", value, "senderShow");
            return (Criteria) this;
        }

        public Criteria andSenderShowLessThanOrEqualTo(Integer value) {
            addCriterion("sender_show <=", value, "senderShow");
            return (Criteria) this;
        }

        public Criteria andSenderShowIn(List<Integer> values) {
            addCriterion("sender_show in", values, "senderShow");
            return (Criteria) this;
        }

        public Criteria andSenderShowNotIn(List<Integer> values) {
            addCriterion("sender_show not in", values, "senderShow");
            return (Criteria) this;
        }

        public Criteria andSenderShowBetween(Integer value1, Integer value2) {
            addCriterion("sender_show between", value1, value2, "senderShow");
            return (Criteria) this;
        }

        public Criteria andSenderShowNotBetween(Integer value1, Integer value2) {
            addCriterion("sender_show not between", value1, value2, "senderShow");
            return (Criteria) this;
        }

        public Criteria andReceiverShowIsNull() {
            addCriterion("receiver_show is null");
            return (Criteria) this;
        }

        public Criteria andReceiverShowIsNotNull() {
            addCriterion("receiver_show is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverShowEqualTo(Integer value) {
            addCriterion("receiver_show =", value, "receiverShow");
            return (Criteria) this;
        }

        public Criteria andReceiverShowNotEqualTo(Integer value) {
            addCriterion("receiver_show <>", value, "receiverShow");
            return (Criteria) this;
        }

        public Criteria andReceiverShowGreaterThan(Integer value) {
            addCriterion("receiver_show >", value, "receiverShow");
            return (Criteria) this;
        }

        public Criteria andReceiverShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("receiver_show >=", value, "receiverShow");
            return (Criteria) this;
        }

        public Criteria andReceiverShowLessThan(Integer value) {
            addCriterion("receiver_show <", value, "receiverShow");
            return (Criteria) this;
        }

        public Criteria andReceiverShowLessThanOrEqualTo(Integer value) {
            addCriterion("receiver_show <=", value, "receiverShow");
            return (Criteria) this;
        }

        public Criteria andReceiverShowIn(List<Integer> values) {
            addCriterion("receiver_show in", values, "receiverShow");
            return (Criteria) this;
        }

        public Criteria andReceiverShowNotIn(List<Integer> values) {
            addCriterion("receiver_show not in", values, "receiverShow");
            return (Criteria) this;
        }

        public Criteria andReceiverShowBetween(Integer value1, Integer value2) {
            addCriterion("receiver_show between", value1, value2, "receiverShow");
            return (Criteria) this;
        }

        public Criteria andReceiverShowNotBetween(Integer value1, Integer value2) {
            addCriterion("receiver_show not between", value1, value2, "receiverShow");
            return (Criteria) this;
        }

        public Criteria andUnreadSenderIsNull() {
            addCriterion("unread_sender is null");
            return (Criteria) this;
        }

        public Criteria andUnreadSenderIsNotNull() {
            addCriterion("unread_sender is not null");
            return (Criteria) this;
        }

        public Criteria andUnreadSenderEqualTo(Integer value) {
            addCriterion("unread_sender =", value, "unreadSender");
            return (Criteria) this;
        }

        public Criteria andUnreadSenderNotEqualTo(Integer value) {
            addCriterion("unread_sender <>", value, "unreadSender");
            return (Criteria) this;
        }

        public Criteria andUnreadSenderGreaterThan(Integer value) {
            addCriterion("unread_sender >", value, "unreadSender");
            return (Criteria) this;
        }

        public Criteria andUnreadSenderGreaterThanOrEqualTo(Integer value) {
            addCriterion("unread_sender >=", value, "unreadSender");
            return (Criteria) this;
        }

        public Criteria andUnreadSenderLessThan(Integer value) {
            addCriterion("unread_sender <", value, "unreadSender");
            return (Criteria) this;
        }

        public Criteria andUnreadSenderLessThanOrEqualTo(Integer value) {
            addCriterion("unread_sender <=", value, "unreadSender");
            return (Criteria) this;
        }

        public Criteria andUnreadSenderIn(List<Integer> values) {
            addCriterion("unread_sender in", values, "unreadSender");
            return (Criteria) this;
        }

        public Criteria andUnreadSenderNotIn(List<Integer> values) {
            addCriterion("unread_sender not in", values, "unreadSender");
            return (Criteria) this;
        }

        public Criteria andUnreadSenderBetween(Integer value1, Integer value2) {
            addCriterion("unread_sender between", value1, value2, "unreadSender");
            return (Criteria) this;
        }

        public Criteria andUnreadSenderNotBetween(Integer value1, Integer value2) {
            addCriterion("unread_sender not between", value1, value2, "unreadSender");
            return (Criteria) this;
        }

        public Criteria andUnreadReceiverIsNull() {
            addCriterion("unread_receiver is null");
            return (Criteria) this;
        }

        public Criteria andUnreadReceiverIsNotNull() {
            addCriterion("unread_receiver is not null");
            return (Criteria) this;
        }

        public Criteria andUnreadReceiverEqualTo(Integer value) {
            addCriterion("unread_receiver =", value, "unreadReceiver");
            return (Criteria) this;
        }

        public Criteria andUnreadReceiverNotEqualTo(Integer value) {
            addCriterion("unread_receiver <>", value, "unreadReceiver");
            return (Criteria) this;
        }

        public Criteria andUnreadReceiverGreaterThan(Integer value) {
            addCriterion("unread_receiver >", value, "unreadReceiver");
            return (Criteria) this;
        }

        public Criteria andUnreadReceiverGreaterThanOrEqualTo(Integer value) {
            addCriterion("unread_receiver >=", value, "unreadReceiver");
            return (Criteria) this;
        }

        public Criteria andUnreadReceiverLessThan(Integer value) {
            addCriterion("unread_receiver <", value, "unreadReceiver");
            return (Criteria) this;
        }

        public Criteria andUnreadReceiverLessThanOrEqualTo(Integer value) {
            addCriterion("unread_receiver <=", value, "unreadReceiver");
            return (Criteria) this;
        }

        public Criteria andUnreadReceiverIn(List<Integer> values) {
            addCriterion("unread_receiver in", values, "unreadReceiver");
            return (Criteria) this;
        }

        public Criteria andUnreadReceiverNotIn(List<Integer> values) {
            addCriterion("unread_receiver not in", values, "unreadReceiver");
            return (Criteria) this;
        }

        public Criteria andUnreadReceiverBetween(Integer value1, Integer value2) {
            addCriterion("unread_receiver between", value1, value2, "unreadReceiver");
            return (Criteria) this;
        }

        public Criteria andUnreadReceiverNotBetween(Integer value1, Integer value2) {
            addCriterion("unread_receiver not between", value1, value2, "unreadReceiver");
            return (Criteria) this;
        }

        public Criteria andChatTypeIsNull() {
            addCriterion("chat_type is null");
            return (Criteria) this;
        }

        public Criteria andChatTypeIsNotNull() {
            addCriterion("chat_type is not null");
            return (Criteria) this;
        }

        public Criteria andChatTypeEqualTo(Integer value) {
            addCriterion("chat_type =", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotEqualTo(Integer value) {
            addCriterion("chat_type <>", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeGreaterThan(Integer value) {
            addCriterion("chat_type >", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("chat_type >=", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLessThan(Integer value) {
            addCriterion("chat_type <", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLessThanOrEqualTo(Integer value) {
            addCriterion("chat_type <=", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeIn(List<Integer> values) {
            addCriterion("chat_type in", values, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotIn(List<Integer> values) {
            addCriterion("chat_type not in", values, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeBetween(Integer value1, Integer value2) {
            addCriterion("chat_type between", value1, value2, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("chat_type not between", value1, value2, "chatType");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}