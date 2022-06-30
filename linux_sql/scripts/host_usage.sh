#! /bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Check # of CLI arguments
if [ $# -ne 5 ]; then
  echo 'Requires host, port, db name, username, and password'
  exit 1
fi

# Save outputs of meminfo, vmstat, and df
meminfo=`cat /proc/meminfo`
vmstat_out=`vmstat -t`
disk_free=`df -BM /`

# Collect the data required to build SQL Query
hostname=$(hostname -f)
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";
timestamp=$(date +"%Y-%m-%d %T")
memory_free=$(echo "$meminfo"  | egrep "^MemFree:" | awk '{print $2}' | xargs)
cpu_idle=$(echo "$vmstat_out"  | awk '{print $15}' | tail -n -1 |  xargs)
cpu_kernel=$(echo "$vmstat_out"  | awk '{print $14}' | tail -n -1 |  xargs)
disk_io=$(echo "$vmstat_out"  | awk '{print $16}' | tail -n -1 |  xargs)
disk_available=$(echo "$disk_free"  | awk '{print $4}' | tail -n -1 |  xargs)

# Build SQL Query
insert_stmt="INSERT INTO host_usage(\"timestamp\", host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
VALUES('$timestamp', $host_id, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, ${disk_available::-1})"

export PGPASSWORD=$psql_password
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?