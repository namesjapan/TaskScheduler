import java.io.*;
import java.util.*;

class Job implements Comparable<Job> {
    int id, processingTime, priority, arrivalTime;

    public Job(int id, int processingTime, int priority, int arrivalTime) {
        this.id = id;
        this.processingTime = processingTime;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public int compareTo(Job other) {
        return Integer.compare(this.processingTime, other.processingTime);
    }
}

public class TaskScheduler {

    public static void task1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("task1-input.txt"));
        List<Job> jobs = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            int id = Integer.parseInt(parts[0]);
            int time = Integer.parseInt(parts[1]);
            jobs.add(new Job(id, time, -1, -1));
        }

        Collections.sort(jobs);

        int currentTime = 0;
        double totalCompletionTime = 0;
        List<Integer> executionOrder = new ArrayList<>();

        for (Job job : jobs) {
            currentTime += job.processingTime;
            totalCompletionTime += currentTime;
            executionOrder.add(job.id);
        }

        System.out.println("Execution order: " + executionOrder);
        System.out.println("Average completion time: " + totalCompletionTime / jobs.size());
    }

    public static void task2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("task2-input.txt"));
        List<Job> jobs = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            int id = Integer.parseInt(parts[0]);
            int time = Integer.parseInt(parts[1]);
            int priority = Integer.parseInt(parts[2]);
            jobs.add(new Job(id, time, priority, -1));
        }

        jobs.sort((a, b) -> {
            if (a.priority == b.priority) {
                return Integer.compare(a.processingTime, b.processingTime);
            } else {
                return Integer.compare(a.priority, b.priority);
            }
        });

        int currentTime = 0;
        double totalCompletionTime = 0;
        List<Integer> executionOrder = new ArrayList<>();

        for (Job job : jobs) {
            currentTime += job.processingTime;
            totalCompletionTime += currentTime;
            executionOrder.add(job.id);
        }

        System.out.println("Execution order: " + executionOrder);
        System.out.println("Average completion time: " + totalCompletionTime / jobs.size());
    }

    public static void task3() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("task3-input.txt"));
        List<Job> jobs = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            int id = Integer.parseInt(parts[0]);
            int time = Integer.parseInt(parts[1]);
            int arrivalTime = Integer.parseInt(parts[2]);
            jobs.add(new Job(id, time, -1, arrivalTime));
        }

        PriorityQueue<Job> pq = new PriorityQueue<>(Comparator.comparingInt(job -> job.processingTime));
        List<Integer> executionOrder = new ArrayList<>();
        double totalCompletionTime = 0;
        int currentTime = 0, index = 0;

        jobs.sort(Comparator.comparingInt(job -> job.arrivalTime));

        while (!pq.isEmpty() || index < jobs.size()) {
            while (index < jobs.size() && jobs.get(index).arrivalTime <= currentTime) {
                pq.add(jobs.get(index));
                index++;
            }

            if (!pq.isEmpty()) {
                Job job = pq.poll();
                currentTime += job.processingTime;
                totalCompletionTime += currentTime;
                executionOrder.add(job.id);
            } else if (index < jobs.size()) {
                currentTime = jobs.get(index).arrivalTime;
            }
        }

        System.out.println("Execution order: " + executionOrder);
        System.out.println("Average completion time: " + totalCompletionTime / jobs.size());
    }

    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
    }
}