package be.soldier.money.persistence.dao;

import be.soldier.money.common.ConvertUtil;
import be.soldier.money.common.LabelCriteria;
import be.soldier.money.common.LabelType;
import be.soldier.money.common.LabelUtil;
import be.soldier.money.model.MoneyLabel;
import be.soldier.money.persistence.jooq.tables.records.LabelRecord;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static be.soldier.money.persistence.jooq.tables.Account.ACCOUNT;
import static be.soldier.money.persistence.jooq.tables.Label.LABEL;

/**
 * Created by soldiertt on 29-01-15.
 */
@Repository("labelDao")
public class LabelDao {

    @Autowired
    private DSLContext create;

    public List<MoneyLabel> findMoneyLabels(LabelCriteria criteria) {

        List<MoneyLabel> labels = new ArrayList<MoneyLabel>();
        Result<Record> result = findLabelsByYear(criteria);

        for (Record record : result) {
            labels.add(LabelUtil.makeMoneyLabel(record));
        }
        return labels;
    }

    public MoneyLabel getLabelById(Integer labelId) {
        Record label = create.select().from(LABEL)
                .leftOuterJoin(ACCOUNT).on(ACCOUNT.ID.equal(LABEL.DEFAULT_RECIPIENT_ID))
                .where(LABEL.ID.equal(labelId)).fetchOne();

        return LabelUtil.makeMoneyLabel(label);
    }

    @Transactional
    public void saveLabel(LabelRecord label) {

        create.insertInto(LABEL,
                LABEL.NAME,
                LABEL.TYPE,
                LABEL.YEAR,
                LABEL.FIXED)
                .values(label.getName(),
                        label.getType(),
                        label.getYear(),
                        label.getFixed()).execute();

    }

    private Result<Record> findLabelsByYear(LabelCriteria criteria) {

        SelectQuery query = create.selectQuery();
        query.addFrom(LABEL);
        query.addJoin(ACCOUNT, JoinType.LEFT_OUTER_JOIN, ACCOUNT.ID.equal(LABEL.DEFAULT_RECIPIENT_ID));
        query.addConditions(LABEL.YEAR.equal(criteria.getYear()));

        if (criteria.getFixed() != null) {
            query.addConditions(LABEL.FIXED.equal(ConvertUtil.toByte(criteria.getFixed())));
        }
        if (criteria.getIncome() != null) {
            query.addConditions(LABEL.INCOME.equal(ConvertUtil.toByte(criteria.getIncome())));
        }
        if (criteria.getLabelType() != null) {
            query.addConditions(LABEL.TYPE.equal(criteria.getLabelType().toString()));
        }
        return query.fetch();
    }

}
