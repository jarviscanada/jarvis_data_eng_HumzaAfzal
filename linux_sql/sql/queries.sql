-- Group hosts by CPU number and sort by memory size
SELECT
    cpu_number,
    id AS host_id,
    total_mem
FROM
    host_info
ORDER BY
    AVG(cpu_number) OVER(
        PARTITION BY cpu_number
        ORDER BY
            total_mem DESC
    );

-- Average memory usage percentage over 5 min intervals for each host
SELECT DISTINCT
    hu.host_id,
    hi.hostname,
    round5(hu."timestamp") AS "timestamp",
    AVG(
    (hi.total_mem - hu.memory_free)* 100 / hi.total_mem
        ) OVER(
        PARTITION BY round5(hu."timestamp"), hu.host_id
        ) AS used_mem_percentage
FROM
    host_usage hu,
    host_info hi
WHERE
        hu.host_id = hi.id;

-- Displays the number of host_usage data collections happened in 5 minute intervals.
SELECT DISTINCT
    host_id,
    round5("timestamp") AS ts,
    COUNT(host_id) OVER(
        PARTITION BY round5("timestamp"), host_id
    ) AS num_data_points
FROM
    host_usage
ORDER BY
    host_id, ts;
