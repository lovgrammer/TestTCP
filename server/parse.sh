tshark -ta -r $1 -T fields -e frame.time -e frame.len -E header=y -E separator=, -E quote=d -E occurrence=f > $2
