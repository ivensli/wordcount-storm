import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.*;

public class ReportBolt extends BaseRichBolt {
    private HashMap<String,Long> counts =null;

    public void prepare(Map config, TopologyContext context, OutputCollector collector){
        this.counts = new HashMap<String, Long>();
    }

    public void execute(Tuple tuple){
        String word = tuple.getStringByField("word");
        Long count = tuple.getLongByField("count");
        this.counts.put(word,count);

    }

    /*
     该bolt位于末端,所以declareOutputFields为空
    **/
    public void declareOutputFields(OutputFieldsDeclarer declarer){

    }


    /*
     cleanup方法用来释放bolt占用的资源
     */
    public void cleanup(){
        System.out.println("--- FINAL COUNTS ---");
        List<String> keys = new ArrayList<String>();
        keys.addAll(this.counts.keySet());
        Collections.sort(keys);
        for(String key: keys){
            System.out.println(key+" : "+this.counts.get(key));
        }
    }
}