package org.crusoe.entity.workflow.normativeDocFiling;

public enum NormativeDocFilingStatus {
	PENDING {
		public String getName() {
			return "待审核";
		}
	},
	ACCEPT {
		public String getName() {
			return "准予备案";
		}
	},
	REFUSE {
		public String getName() {
			return "不予备案";
		}
	},
	REVISE {
		public String getName() {
			return "待修改";
		}
	};

}
