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

# Save outputs of meminfo and lscpu
meminfo=`cat /proc/meminfo`
lscpu_out=`lscpu`

# Collect the data required to build SQL Query
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model\sname:" | awk '{print $3,$4,$5,$6,$7}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU\sMHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out"  | egrep "^L2\scache:" | awk '{print $3}' | xargs)
total_mem=$(echo "$meminfo"  | egrep "^MemTotal:" | awk '{print $2}' | xargs)
timestamp=$(date +"%Y-%m-%d %T")

# Build SQL Query
insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem, \"timestamp\")
VALUES ('$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, ${l2_cache::-1}, $total_mem, '$timestamp');"

export PGPASSWORD=$psql_password
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?