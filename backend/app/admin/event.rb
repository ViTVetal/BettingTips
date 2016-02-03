ActiveAdmin.register Event do

# See permitted parameters documentation:
# https://github.com/activeadmin/activeadmin/blob/master/docs/2-resource-customization.md#setting-up-strong-parameters
#
# permit_params :list, :of, :attributes, :on, :model
#
# or
#
# permit_params do
#   permitted = [:permitted, :attributes]
#   permitted << :other if resource.something?
#   permitted
# end

  permit_params :team1, :team2, :tip, :date, :category_id, :league_id,
            :success, :odds, :score1, :score2, :image_url

  config.action_items[0] = ActiveAdmin::ActionItem.new :view, only: [:index, :show] do
    link_to('New Event', new_resource_path)
  end
  
  collection_action :get_autocomplete_items, :method => :get
  
  controller do
    def get_autocomplete_items
      teams1 = Event.select(:team1).where("lower(team1) LIKE ?", "%#{params[:term].downcase}%") 
      teams2 = Event.select(:team2).where("lower(team2) LIKE ?", "%#{params[:term].downcase}%")   
      render json: custom_json_for(teams1, teams2)
    end

    private
      def custom_json_for(teams1, teams2)
        result1 = teams1.map do |item|
          { :id => " #{item.team1}",
            :label => item.team1,
            :value => item.team1
          }
        end

        result2 = teams2.map do |item|
          { :id => " #{item.team2}",
            :label => item.team2,
            :value => item.team2
          }
        end
        list = result1 + result2
        list.uniq.to_json
      end
  end

  form do |f|
    inputs do
      f.input :category
      f.input :league
      f.input :team1, :as => :autocomplete, :url => get_autocomplete_items_admin_events_path
      f.input :team2, :as => :autocomplete, :url => get_autocomplete_items_admin_events_path
      f.input :tip
      f.input :date, as: :date_picker, :input_html => { class: "datepicker", value: f.object.date.blank? ? Time.now.strftime('%Y-%m-%d') : f.object.date}
      f.input :odds
      f.input :score1
      f.input :score2
      f.input :success, as: :radio, collection: [['Success', true], ['Fail', false]], label: ""
    end

    actions
  end

end
 