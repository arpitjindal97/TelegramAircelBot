
set :docker_repo, "arpitjindal1997/telegram-aircel-bot"

task :deploy do

	on "root@139.59.71.230" do
		execute "export docker_cont_id=$(docker ps -a | grep #{fetch(:docker_repo)} | cut -d ' ' -f 1 ) 
	       	if [ \"$docker_cont_id\" == \"\" ]; then exit 0
		else 	docker stop $docker_cont_id 
			docker rm $docker_cont_id
			docker rmi #{fetch(:docker_repo)}
		fi"
		execute "docker pull #{fetch(:docker_repo)}  > /dev/null "
		execute "docker run -d #{fetch(:docker_repo)}"
	end
end