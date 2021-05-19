package howard.cinema.core.dao.dict.acl;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @name: PositionsType
 * @description: 职级分类
 * @author: huojiajin
 * @time: 2020/6/18 16:27
 */
public enum PositionsType {
    FZG("非主管"){
        @Override
        public List<PositionsClass> getPositionsClass() {
            return Lists.newArrayList(PositionsClass.TC, PositionsClass.PBC);
        }
    },
    BC("组经理"){
        @Override
        public List<PositionsClass> getPositionsClass() {
            return Lists.newArrayList(PositionsClass.BC, PositionsClass.SBC);
        }
    },
    BM("区域总经理"){
        @Override
        public List<PositionsClass> getPositionsClass() {
            return Lists.newArrayList(PositionsClass.BM, PositionsClass.SBM);
        }
    },
    AS("总监") {
        @Override
        public List<PositionsClass> getPositionsClass() {
            return Lists.newArrayList(PositionsClass.AS, PositionsClass.SAS, PositionsClass.GAS);
        }
    },
    ;

    PositionsType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public abstract List<PositionsClass> getPositionsClass();

    public static PositionsType fromClass(PositionsClass positionsClass) throws InterruptedException {
        for (PositionsType type : PositionsType.values()) {
            List<PositionsClass> classes = type.getPositionsClass();
            if (!CollectionUtils.isEmpty(classes)){
                for (PositionsClass oriClass : classes) {
                    if (oriClass == positionsClass){
                        return type;
                    }
                }
            }
        }
        throw new InterruptedException("职级" + positionsClass.getValue() + "不存在分类");
    }
}
