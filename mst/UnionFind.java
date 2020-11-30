import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnionFind {

    private Map<Integer, Integer> weights;
    private Map<Integer, Integer> parents;

    public UnionFind() {
        this.weights = new HashMap<>();
        this.parents = new HashMap<>();
    }

    public int get(int obj) {
        if (!parents.containsKey(obj)) {
            this.parents.put(obj, obj);
            this.weights.put(obj, 1);
            return obj;
        }

        List<Integer> path = new ArrayList<>();
        path.add(obj);
        int root = this.parents.get(obj);

        // find path of objects leading to the root
        while (root != path.get(path.size() - 1)) {
            path.add(root);
            root = this.parents.get(root);
        }

        // compress the path and return
        for (int prev : path) {
            this.parents.put(prev, root);
        }
        return root;
    }

    public void union(int src, int dst) {
        List<Integer> roots = new ArrayList<>();
        roots.add(this.get(src));
        roots.add(this.get(dst));

        int heaviest = 0;
        int src_w = this.weights.get(roots.get(0));
        int dst_w = this.weights.get(roots.get(1));

        if (src_w > dst_w) {
            heaviest = roots.get(0);
        } else if (src_w < dst_w) {
            heaviest = roots.get(1);
        } else {
            if (roots.get(0) > roots.get(1)) {
                heaviest = roots.get(0);
            } else {
                heaviest = roots.get(1);
            }
        }

        for (int r : roots) {
            if (r != heaviest) {
                int tmp = this.weights.get(heaviest) + this.weights.get(r);
                this.weights.put(heaviest, tmp);
                this.parents.put(r, heaviest);
            }
        }
    }
}