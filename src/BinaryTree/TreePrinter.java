package BinaryTree;

import java.util.ArrayList;
import java.util.List;

public class TreePrinter extends BTree {
    String space = " ";

    @Override
    public void run() {
        List<List<Node>> layers = new ArrayList<>();

        traverse(root, 0, layers);

        int finalRowItems = (int) Math.pow(2.0, layers.size() - 1);
        int length = finalRowItems * 2 - 1;
        for (int i = 0; i < layers.size(); i++) {
            int numOfItems = (int) Math.pow(2.0, i);
            int gap = length / numOfItems;
            List<Node> layer = layers.get(i);
            StringBuilder layerBuilder = new StringBuilder();

            for (int j = 0; j < numOfItems; j++) {
                Node node = j >= layer.size() ? null : layer.get(j);
                if (node != null) {
                    layerBuilder.append(node.val);
                } else {
                    layerBuilder.append(space);
                }

                if (j != numOfItems - 1) {
                    layerBuilder.append(space.repeat(gap));
                }
            }

            String layerStr = center(layerBuilder, length);

            if (i > 0) {
                int edgeLength = (int) Math.pow(2, layers.size() - i - 1);
                assert edgeLength >= 1;
                StringBuilder edges = new StringBuilder();
                int edgeIncrement = 2; // for the / and the \ == 2
                int innerEdgeGap = 1; // /..\
                int prevNumOfItems = (int) Math.pow(2.0, i - 1);
                int prevGap = length / prevNumOfItems;
                int outerEdgeGap = prevGap - 2; // /\...../\

                for (int j = 1; j <= edgeLength; j++) {
                    StringBuilder edge = new StringBuilder();
                    for (int k = 0; k < numOfItems; k++) {
                        if (k >= layer.size() || layer.get(k) == null) {
                            edge.append(space);
                        } else {
                            if (k % 2 == 0) {
                                edge.append("/");
                            } else {
                                edge.append("\\");
                            }
                        }

                        if (k == numOfItems - 1) {
                            continue;
                        }

                        if (k % 2 == 0) {
                            edge.append(space.repeat(innerEdgeGap));
                        } else {
                            edge.append(space.repeat(Math.max(outerEdgeGap, 0)));
                        }
                    }

                    innerEdgeGap += edgeIncrement;
                    outerEdgeGap -= edgeIncrement;

                    edges.append(center(edge, length));
                    edges.append('\n');
                }

                layerStr = edges + layerStr;
            }

            System.out.println(layerStr);
        }
    }

    /*
    "..O.."
    "./.\."
    "O...X"

    .....O.....
    ..../.\....
    .../...\...
    ..O.....O..
    ./.\.../.\.
    O...X.O...X

    "O"
    "|"
    "X"

    "..O.."
    "./.\."
    "O...O"
    "|...|"
    "O...O"
    */
    private void traverse(Node node, int depth, List<List<Node>> layers) {
        if (node == null) {
            return;
        }

        if (depth >= layers.size()) {
            assert depth == layers.size();
            layers.add(new ArrayList<>());
        }

        List<Node> layer = layers.get(depth);
        layer.add(node);

        traverse(node.left, depth + 1, layers);
        traverse(node.right, depth + 1, layers);
    }

    private <T> String center(T obj, int length) {
        String str = obj.toString();
        String pad = " ";

        if (str == null || length <= str.length()) {
            return str;
        }

        StringBuilder out = new StringBuilder(length);
        out.append(pad.repeat((length - str.length()) / 2));
        out.append(str);

        while (out.length() < length) {
            out.append(pad);
        }

        return out.toString();
    }
}
