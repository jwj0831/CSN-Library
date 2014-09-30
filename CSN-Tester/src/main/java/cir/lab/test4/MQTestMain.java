package cir.lab.test4;

public class MQTestMain {
    public static void main(String args[]) {

        CSNSubscriber sub = new CSNSubscriber();
        sub.setConnection("tcp://117.16.146.55:1883", "TestApp", "CSN.Test.Topic");
        sub.setMessageCallback( new MessageCallback() {
            @Override
            public void messageArrived(String data) {
                System.out.println(data);
            }
        });
        sub.subscribe();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sub.unsubscribe();
    }
}
