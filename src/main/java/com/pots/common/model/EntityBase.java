package com.pots.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * @Dataは必要、でないと子クラスは親クラスのメンバー変数にアクセス出来ないため（但しMapperは正常に動作する）
 * toStringはアドレス値になり、@ToStringが必要、equalsもアドレス値が違うため、結果は常にfalse、@EqualsAndHashCodeが必要
 */

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public abstract class EntityBase extends EntityBaseOrigin {
    
    private Boolean isActive;     // not null default true
    
}
