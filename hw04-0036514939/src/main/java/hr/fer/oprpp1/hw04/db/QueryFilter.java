package hr.fer.oprpp1.hw04.db;

import java.util.List;

public class QueryFilter implements IFilter {
	private List<ConditionalExpression> list;
	
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		
		for(ConditionalExpression ce : list) {
			if(!ce.getComparisonOperator().satisfied(ce.getFieldGetter().get(record), ce.getStringLiteral())) {
				return false;
			}
		}
		
		return true;
	}

}
